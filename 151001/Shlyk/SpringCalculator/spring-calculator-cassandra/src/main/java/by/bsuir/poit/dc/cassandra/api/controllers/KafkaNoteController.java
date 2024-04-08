package by.bsuir.poit.dc.cassandra.api.controllers;

import by.bsuir.poit.dc.cassandra.api.dto.mappers.NoteMapper;
import by.bsuir.poit.dc.cassandra.api.dto.request.UpdateNoteDto;
import by.bsuir.poit.dc.cassandra.api.dto.response.NoteDto;
import by.bsuir.poit.dc.cassandra.api.exceptions.ContentNotValidException;
import by.bsuir.poit.dc.cassandra.api.exceptions.ResourceModifyingException;
import by.bsuir.poit.dc.cassandra.api.exceptions.ResourceNotFoundException;
import by.bsuir.poit.dc.cassandra.services.ModerationService;
import by.bsuir.poit.dc.cassandra.services.NoteService;
import by.bsuir.poit.dc.dto.groups.Create;
import by.bsuir.poit.dc.dto.groups.Update;
import by.bsuir.poit.dc.kafka.dto.*;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;

/**
 * @author Paval Shlyk
 * @since 06/04/2024
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class KafkaNoteController {
    @Value("${discussion.topics.target}")
    @Setter
    private String targetTopicName;
    private final NoteService noteService;
    private final KafkaTemplate<UUID, NoteResponse> kafkaTemplate;
    private final NoteMapper noteMapper;
    private final Map<RequestEvent, BiFunction<NoteRequest, UpdateNoteDto, NoteResponse>> handlers = Map.of(
	RequestEvent.CREATE, this::createNewsNote,
	RequestEvent.UPDATE, this::updateNoteById,
	RequestEvent.DELETE_BY_ID, this::deleteNoteById,
	RequestEvent.FIND_BY_ID, this::getNoteById,
	RequestEvent.FIND_BY_NEWS_ID, this::getNoteByNewsId,
	RequestEvent.FIND_ALL, this::getAllNotes
    );

    @Deprecated
    public NoteResponse getAllNotes(
	NoteRequest request,
	@Null UpdateNoteDto dto
    ) {
	List<NoteDto> list = noteService.getAll();
	List<KafkaNoteDto> kafkaList = noteMapper.buildRequestList(list);
	return NoteResponse.builder()
		   .status(ResponseEvent.OK)
		   .list(kafkaList)
		   .build();
    }

    public NoteResponse createNewsNote(
	NoteRequest request,
	@Validated(Create.class) UpdateNoteDto dto) {
	NoteDto response = noteService.save(dto);
	KafkaNoteDto kafkaDto = noteMapper.buildResponse(response);
	return NoteResponse.builder()
		   .status(ResponseEvent.OK)
		   .list(List.of(kafkaDto))
		   .build();
    }

    public NoteResponse getNoteByNewsId(
	NoteRequest request,
	@Null UpdateNoteDto _dto) {
	if (request.id() == null) {
	    return NoteResponse.withStatus(ResponseEvent.INVALID_FORMAT);
	}
	long newsId = request.id();
	List<NoteDto> list = noteService.getAllByNewsId(newsId);
	List<KafkaNoteDto> kafkaList = noteMapper.buildRequestList(list);
	assert list != null;
	return NoteResponse.builder()
		   .status(ResponseEvent.OK)
		   .list(kafkaList)
		   .build();
    }

    public NoteResponse getNoteById(
	NoteRequest request,
	@Null UpdateNoteDto _dto) {
	if (request.id() == null) {
	    return NoteResponse.withStatus(ResponseEvent.INVALID_FORMAT);
	}
	long noteId = request.id();
	NoteDto response = noteService.getById(noteId);
	KafkaNoteDto kafkaDto = noteMapper.buildResponse(response);
	return NoteResponse.builder()
		   .status(ResponseEvent.OK)
		   .list(List.of(kafkaDto))
		   .build();
    }

    public NoteResponse updateNoteById(
	NoteRequest request,
	@Validated(Update.class) UpdateNoteDto dto) {
	if (request.id() == null) {
	    return NoteResponse.withStatus(ResponseEvent.INVALID_FORMAT);
	}
	long noteId = request.id();
	NoteDto response = noteService.update(noteId, dto);
	KafkaNoteDto kafkaDto = noteMapper.buildResponse(response);
	return NoteResponse.builder()
		   .status(ResponseEvent.OK)
		   .list(List.of(kafkaDto))
		   .build();
    }

    public NoteResponse deleteNoteById(
	NoteRequest request,
	@Null UpdateNoteDto _dto) {
	if (request.id() == null) {
	    return NoteResponse.withStatus(ResponseEvent.INVALID_FORMAT);
	}
	long noteId = request.id();
	var status = noteService.delete(noteId).isPresent()
			 ? ResponseEvent.OK
			 : ResponseEvent.NOT_FOUND;
	return NoteResponse.withStatus(status);
    }

    @KafkaListener(topics = "${discussion.topics.source}")
    public void processNoteRequest(ConsumerRecord<UUID, NoteRequest> record) {
	UUID key = record.key();
	NoteRequest request = record.value();
	if (key == null || request == null) {
	    log.error(STR."Failed to process request with key=\{key} and value=\{request}");
	    return;
	}
	NoteResponse response;
	try {
	    RequestEvent event = request.event();
	    UpdateNoteDto dto = noteMapper.unwrapRequest(request.dto());
	    var function = handlers.get(event);
	    assert function != null : "Event handler cannot be null";
	    response = function.apply(request, dto);
	} catch (Throwable t) {
	    log.error(STR."Exception is caught = \{t.getMessage()}");
	    Throwable cause = t.getCause() != null ? t.getCause() : t;
	    ResponseEvent status = switch (cause) {
		case MethodArgumentNotValidException _,
			 ResourceModifyingException _,
			 ContentNotValidException _ -> ResponseEvent.INVALID_FORMAT;
		case ResourceNotFoundException _ -> ResponseEvent.NOT_FOUND;
		default -> ResponseEvent.BUSY;
	    };
	    response = NoteResponse.withStatus(status);
	}
	kafkaTemplate.send(targetTopicName, key, response);
    }
}
