package by.bsuir.poit.dc.cassandra.services.impl;

import by.bsuir.poit.dc.cassandra.api.dto.request.UpdateNoteDto;
import by.bsuir.poit.dc.cassandra.api.dto.response.NoteDto;
import by.bsuir.poit.dc.cassandra.api.dto.response.PresenceDto;
import by.bsuir.poit.dc.cassandra.api.exceptions.ResourceModifyingException;
import by.bsuir.poit.dc.cassandra.api.exceptions.ResourceNotFoundException;
import by.bsuir.poit.dc.cassandra.api.dto.mappers.NoteMapper;
import by.bsuir.poit.dc.cassandra.dao.NoteByNewsRepository;
import by.bsuir.poit.dc.cassandra.dao.NoteByIdRepository;
import by.bsuir.poit.dc.cassandra.model.NoteBuilder;
import by.bsuir.poit.dc.cassandra.model.NoteById;
import by.bsuir.poit.dc.cassandra.model.NoteByNews;
import by.bsuir.poit.dc.cassandra.services.ModerationResult;
import by.bsuir.poit.dc.cassandra.services.ModerationService;
import by.bsuir.poit.dc.cassandra.services.NoteService;
import by.bsuir.poit.dc.context.CatchLevel;
import by.bsuir.poit.dc.context.CatchThrows;
import by.bsuir.poit.dc.context.IdGenerator;
import com.google.errorprone.annotations.Keep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static by.bsuir.poit.dc.cassandra.model.NoteBuilder.*;

/**
 * @author Paval Shlyk
 * @since 06/03/2024
 */
@Slf4j
@Component
@RequiredArgsConstructor
@CatchLevel(DataAccessException.class)
public class NoteServiceImpl implements NoteService {
    private final NoteByIdRepository noteByIdRepository;
    private final NoteByNewsRepository noteByNewsRepository;
    private final NoteMapper noteMapper;
    private final IdGenerator idGenerator;
    private final ModerationService moderationService;

    @Override
    @Transactional
    @CatchThrows(
	call = "newNoteModifyingException",
	args = "noteId")
    public PresenceDto delete(long noteId) {
	Optional<NoteById> noteOptional = noteByIdRepository.findById(noteId);
	if (noteOptional.isPresent()) {
	    var note = noteOptional.get();
	    noteByNewsRepository.deleteByIdAndNewsId(noteId, note.getNewsId());
	    noteByIdRepository.deleteById(noteId);
	}
	return PresenceDto.wrap(noteOptional.isPresent());
    }


    @Override
    @Transactional
    @CatchThrows(
	call = "newNoteAlreadyPresentException")
    public NoteDto save(UpdateNoteDto dto) {
	assert dto.content() != null : "The initial content should be non null";
	var status = switch (moderationService.verify(dto.content())) {
	    case ModerationResult.Error(String _) -> Status.DECLINED;
	    case ModerationResult.Ok _ -> Status.APPROVED;
	};
	long id = idGenerator.nextLong();
	NoteById entity = noteMapper.toEntityById(id, dto);
	entity.setStatus(status.id());
	NoteByNews noteByNews = noteMapper.toEntityByNews(id, dto);
	noteByNews.setStatus(status.id());
	NoteById saved = noteByIdRepository.save(entity);
	NoteByNews _ = noteByNewsRepository.save(noteByNews);
	return noteMapper.toDto(saved);
    }

    @Override
    public List<NoteDto> getAllByNewsId(long newsId) {
	List<NoteByNews> entities = noteByNewsRepository.findByNewsId(newsId);
	return noteMapper.toDtoListFromNoteByNews(entities);
    }

    @Override
    @Transactional
    @CatchThrows(
	call = "newNoteModifyingException",
	args = "noteId")
    public NoteDto update(long noteId, UpdateNoteDto dto) {
	final Status status;
	if (dto.content() != null) {
	    status = switch (moderationService.verify(dto.content())) {
		case ModerationResult.Error(String _) -> Status.DECLINED;
		case ModerationResult.Ok _ -> Status.APPROVED;
	    };
	} else {
	    status = null;
	}
	NoteById noteById = noteByIdRepository
				.findById(noteId)
				.orElseThrow(() -> newNoteNotFountException(noteId));
	NoteByNews noteByNews = noteByNewsRepository
				    .findByIdAndNewsId(noteId, noteById.getNewsId())
				    .orElseThrow(() -> newNoteNotFountException(noteId));
	if (status != null) {
	    noteById.setStatus(status.id());
	    noteByNews.setStatus(status.id());
	}
	NoteById _ = noteMapper.partialUpdate(noteById, dto);
	NoteByNews _ = noteMapper.partialUpdate(noteByNews, dto);
	NoteById saved = noteByIdRepository.save(noteById);
	NoteByNews _ = noteByNewsRepository.save(noteByNews);
	return noteMapper.toDto(saved);
    }

    @Override
    public NoteDto getById(long noteId) {
	NoteById entity = noteByIdRepository
			      .findById(noteId)
			      .orElseThrow(() -> newNoteNotFountException(noteId));
	return noteMapper.toDto(entity);
    }


    @Keep
    private static ResourceNotFoundException newNoteNotFountException(long noteId) {
	final String msg = STR."Failed to find news' note by id = \{noteId}";
	log.warn(msg);
	return new ResourceNotFoundException(msg, 45);
    }

    @Keep
    private static ResourceModifyingException newNoteModifyingException(
	long noteId,
	Throwable cause
    ) {
	final String msg = STR."Failed to modify note by id =\{noteId} by cause=\{cause.getMessage()}";
	final String front = STR."Failed to modify note by id = \{noteId}";
	log.warn(msg);
	return new ResourceModifyingException(front, 70);
    }

    @Keep
    private static ResourceModifyingException newNoteAlreadyPresentException(
	Throwable cause
    ) {
	final String msg = STR."Failed to create new note by cause=\{cause.getMessage()}";
	final String front = "Failed to create new note";
	log.warn(msg);
	return new ResourceModifyingException(front, 70);
    }
}
