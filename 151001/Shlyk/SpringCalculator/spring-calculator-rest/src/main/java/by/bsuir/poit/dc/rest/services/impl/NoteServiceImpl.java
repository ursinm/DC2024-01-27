package by.bsuir.poit.dc.rest.services.impl;

import by.bsuir.poit.dc.LanguageQualityParser;
import by.bsuir.poit.dc.context.CatchLevel;
import by.bsuir.poit.dc.kafka.dto.KafkaUpdateNoteDto;
import by.bsuir.poit.dc.kafka.dto.NoteRequest;
import by.bsuir.poit.dc.kafka.dto.NoteResponse;
import by.bsuir.poit.dc.kafka.dto.RequestEvent;
import by.bsuir.poit.dc.kafka.service.AbstractReactiveKafkaService;
import by.bsuir.poit.dc.rest.api.dto.mappers.NoteMapper;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateNoteDto;
import by.bsuir.poit.dc.rest.api.dto.response.NoteDto;
import by.bsuir.poit.dc.rest.api.dto.response.PresenceDto;
import by.bsuir.poit.dc.rest.api.exceptions.ResourceBusyException;
import by.bsuir.poit.dc.rest.api.exceptions.ResourceModifyingException;
import by.bsuir.poit.dc.rest.api.exceptions.ResourceNotFoundException;
import by.bsuir.poit.dc.rest.services.NewsService;
import by.bsuir.poit.dc.rest.services.NoteService;
import com.google.errorprone.annotations.Keep;
import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.util.Pair;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static by.bsuir.poit.dc.LanguageQualityParser.OrderType;

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
    public void processIncoming(ConsumerRecord<UUID, NoteResponse> response) {
	log.trace("Ready to process incoming record: {}\n", response);
	super.consumerResponse(response);
    }

    private Pair<UUID, Mono<NoteResponse>> send(NoteRequest request) {
	var key = super.nextSessionId();
	var mono = super.nextMonoResponse(key);//bind key for response
	kafkaTemplate.send(targetTopic, key, request);
	return Pair.of(key, mono);
    }

    @Deprecated
    private NoteResponse sendBlocking(NoteRequest request) {
	Pair<UUID, Mono<NoteResponse>> pair = send(request);
	var key = pair.getFirst();
	var mono = pair.getSecond();
	NoteResponse response = mono.block(Duration.ofSeconds(1));
	if (response == null) {
	    throw newResourceBusyException(key, "Time is up!");
	}
	return response;
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
	var request = NoteRequest.builder()
			  .event(RequestEvent.FIND_BY_ID)
			  .id(noteId).dto(null)
			  .build();
	NoteResponse response = sendBlocking(request);
	return noteMapper.unwrapResponse(response.list().getFirst());
    }

    @Override
    public List<NoteDto> getAllByNewsId(long newsId) {
	var request = NoteRequest.withId(RequestEvent.FIND_BY_NEWS_ID, newsId);
	NoteResponse response = sendBlocking(request);
	return noteMapper.unwrapResponseList(response.list());
    }

    @Override
    @Deprecated
    public List<NoteDto> getAll() {
	var request = NoteRequest.builder()
			  .id(null).dto(null)
			  .event(RequestEvent.FIND_ALL)
			  .build();
	NoteResponse response = sendBlocking(request);
	return noteMapper.unwrapResponseList(response.list());
    }

    @Override
    public NoteDto save(UpdateNoteDto dto, long newsId, @Nullable String language) {
//	if (newsService.existsById(newsId).isPresent()) {
//	    throw newNewsNotFoundException(newsId);
//	}
	final List<String> countries = fetchCountries(language);
	KafkaUpdateNoteDto kafkaDto = noteMapper.buildRequest(dto, countries);
	var request = NoteRequest.builder()
			  .event(RequestEvent.CREATE)
			  .id(null).dto(kafkaDto)
			  .build();
	NoteResponse response = sendBlocking(request);
	return noteMapper.unwrapResponse(response.list().getFirst());
    }

    @Override
    public NoteDto update(long noteId, UpdateNoteDto dto, @Nullable String language) {
	final List<String> countries = fetchCountries(language);
	KafkaUpdateNoteDto kafkaDto = noteMapper.buildRequest(dto, countries);
	var request = NoteRequest.builder()
			  .event(RequestEvent.UPDATE)
			  .id(noteId).dto(kafkaDto)
			  .build();
	NoteResponse response = sendBlocking(request);
	return noteMapper.unwrapResponse(response.list().getFirst());
    }


    @Override
    public PresenceDto delete(long noteId) {
	var request = NoteRequest.builder()
			  .event(RequestEvent.DELETE_BY_ID)
			  .id(noteId).dto(null)
			  .build();
	NoteResponse response = sendBlocking(request);
	boolean isDeleted = switch (response.status()) {
	    case OK -> true;
	    default -> false;
	};
	return PresenceDto.wrap(isDeleted);
    }

    private List<String> fetchCountries(@Nullable String language) {
	return Optional.ofNullable(language)
		   .flatMap(lang -> parser.parse(lang, OrderType.PREFERABLE))
		   .orElseGet(this::defaultCountries);
    }
    @Keep
    private static ResourceNotFoundException newNewsNotFoundException(long newsId) {
	final String msg = STR."Any interaction with note is forbidden because corresponding news is not present. News id = \{newsId}";
	log.warn(msg);
	return new ResourceNotFoundException(msg, 142);

    }

    @Keep
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
	return newResourceBusyException(sessionId, "Server is busy");
    }

    @Override
    protected Throwable newEntityNotFoundException(UUID sessionId) {
	final String msg = "Related note or news is not found";
	log.warn(msg);
	return new ResourceNotFoundException(msg, 147);

    }

    @Override
    protected Throwable newBadRequestException(UUID sessionId) {
	final String msg = "Failed save note> Bad request format";
	log.warn(msg);
	return new ResourceModifyingException(msg, 146);

    }

    @Keep
    private static ResourceBusyException newResourceBusyException(UUID sessionId, String reason) {
	final String msg = STR."Failed to access resource with session id = \{sessionId} by reason: \{reason}";
	log.warn(msg);
	throw new ResourceBusyException(reason, 144);
    }
}
