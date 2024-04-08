package by.bsuir.poit.dc.rest.services.impl;

import by.bsuir.poit.dc.LanguageQualityParser;
import by.bsuir.poit.dc.context.CatchLevel;
import by.bsuir.poit.dc.kafka.dto.*;
import by.bsuir.poit.dc.kafka.service.AbstractReactiveKafkaService;
import by.bsuir.poit.dc.rest.api.dto.mappers.NoteMapper;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateNoteDto;
import by.bsuir.poit.dc.rest.api.dto.response.NoteDto;
import by.bsuir.poit.dc.rest.api.dto.response.PresenceDto;
import by.bsuir.poit.dc.rest.api.exceptions.ResourceBusyException;
import by.bsuir.poit.dc.rest.api.exceptions.ResourceNotFoundException;
import by.bsuir.poit.dc.rest.services.NewsService;
import by.bsuir.poit.dc.rest.services.NoteService;
import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.translate.UnicodeUnescaper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static by.bsuir.poit.dc.LanguageQualityParser.*;

/**
 * @author Paval Shlyk
 * @since 04/04/2024
 */
@Slf4j
@Service
@CatchLevel(DataAccessException.class)
@RequiredArgsConstructor
public class NoteServiceImpl extends AbstractReactiveKafkaService<NoteResponse> implements NoteService {
    private final NewsService newsService;
    private final LanguageQualityParser parser;
    private final NoteMapper noteMapper;
    @Value("${location.default}")
    @Setter
    private String defaultCountry;
    @Value("${publisher.topics.target}")
    @Setter
    private String targetTopic;
    private final KafkaTemplate<UUID, NoteRequest> kafkaTemplate;

    @KafkaListener(topics = "${publisher.topics.source}")
    public void processIncoming(ConsumerRecord<UUID, NoteResponse> record) {

    }

    private void send(UUID key, NoteRequest request) {
	kafkaTemplate.send(targetTopic, key, request);
    }

    @PostConstruct
    public void init() {
	if (defaultCountry == null) {
	    final String msg = "The default country is not provided";
	    log.error(msg);
	    throw new IllegalStateException(msg);
	}
	if (targetTopic == null) {
	    final String msg = "The target topic is not specified";
	    log.error(msg);
	    throw new IllegalStateException(msg);
	}
    }

    @Override
    public NoteDto getById(long noteId) {
	return null;
    }

    @Override
    public List<NoteDto> getAllByNewsId(long newsId) {
	return null;
    }

    @Override
    public List<NoteDto> getAll() {
	return null;
    }

    @Override
    public NoteDto save(UpdateNoteDto dto, long newsId, @Nullable String language) {
	if (newsService.existsById(newsId).isPresent()) {
	    throw newNewsNotFoundException(newsId);
	}
	final List<String> countries = Optional.ofNullable(language)
					   .flatMap(lang -> parser.parse(lang, OrderType.PREFERABLE))
					   .orElseGet(this::defaultCountries);
	KafkaUpdateNoteDto kafkaDto = noteMapper.buildRequest(dto, countries);
	var id = nextSessionId();
	var request = NoteRequest.builder()
			  .event(RequestEvent.CREATE)
			  .id(null).dto(kafkaDto)
			  .build();
	var mono = nextMonoResponse(id);
	send(id, request);
	return mono.map(response -> noteMapper.unwrapResponse(response.list().getFirst()))
		   .block(Duration.ofSeconds(1));
//	throw newNoteCreationException(newsId, kafkaDto);
    }

    @Override
    public NoteDto update(long noteId, UpdateNoteDto dto, @Nullable String language) {
	return null;
    }


    @Override
    public PresenceDto delete(long noteId) {
	return null;
    }

    private static ResourceNotFoundException newNewsNotFoundException(long newsId) {
	final String msg = STR."Any interaction with note is forbidden because corresponding news is not present. News id = \{newsId}";
	log.warn(msg);
	return new ResourceNotFoundException(msg, 142);

    }

    private static ResourceBusyException newNoteCreationException(long newsId, KafkaUpdateNoteDto dto) {
	final String msg = STR."Failed to create note for news by id = \{newsId} for dto = \{dto}";
	log.warn(msg);
	return new ResourceBusyException(msg, 143);

    }

    private List<String> defaultCountries() {
	return List.of(defaultCountry);
    }

    @Override
    protected Throwable newServerBusyException(UUID sessionId) {
	return null;
    }

    @Override
    protected Throwable newEntityNotFoundException(UUID sessionId) {
	return null;
    }

    @Override
    protected Throwable newBadRequestException(UUID sessionId) {
	return null;
    }
}
