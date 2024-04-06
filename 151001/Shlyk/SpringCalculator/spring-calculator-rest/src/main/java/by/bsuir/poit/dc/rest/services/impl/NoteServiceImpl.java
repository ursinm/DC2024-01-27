package by.bsuir.poit.dc.rest.services.impl;

import by.bsuir.poit.dc.LanguageQualityParser;
import by.bsuir.poit.dc.context.CatchLevel;
import by.bsuir.poit.dc.kafka.dto.KafkaNoteDto;
import by.bsuir.poit.dc.kafka.dto.KafkaUpdateNoteDto;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static by.bsuir.poit.dc.LanguageQualityParser.*;

/**
 * @author Paval Shlyk
 * @since 04/04/2024
 */
@Slf4j
@Service
@CatchLevel(DataAccessException.class)
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NewsService newsService;
    private final LanguageQualityParser parser;
    private final RestTemplate cassandraTemplate;
    private final NoteMapper noteMapper;
    @Value("${location.default}")
    private String defaultCountry;

    @PostConstruct
    public void init() {
	if (defaultCountry == null) {
	    final String msg = "The default country is not provided";
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
	ResponseEntity<KafkaNoteDto> response = cassandraTemplate.postForEntity("notes", kafkaDto, KafkaNoteDto.class);
	if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
	    return noteMapper.unwrapResponse(response.getBody());
	}
	if (!response.hasBody()) {
	    final String msg = "Kafka body is null and bad status expected";
	    log.error(msg);
	    throw new IllegalStateException(msg);//there is no message for front; the server is broken
	}
	throw newNoteCreationException(newsId, kafkaDto);
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
}
