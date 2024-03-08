package by.bsuir.poit.dc.cassandra.services.impl;

import by.bsuir.poit.dc.cassandra.api.dto.request.UpdateNoteDto;
import by.bsuir.poit.dc.cassandra.api.dto.response.NoteDto;
import by.bsuir.poit.dc.cassandra.api.dto.response.PresenceDto;
import by.bsuir.poit.dc.cassandra.api.exceptions.ResourceModifyingException;
import by.bsuir.poit.dc.cassandra.api.exceptions.ResourceNotFoundException;
import by.bsuir.poit.dc.cassandra.api.mappers.NoteMapper;
import by.bsuir.poit.dc.cassandra.dao.NoteRepository;
import by.bsuir.poit.dc.cassandra.model.Note;
import by.bsuir.poit.dc.cassandra.services.NoteService;
import by.bsuir.poit.dc.context.CatchLevel;
import by.bsuir.poit.dc.context.CatchThrows;
import com.google.errorprone.annotations.Keep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Paval Shlyk
 * @since 06/03/2024
 */
@Slf4j
@Component
@RequiredArgsConstructor
@CatchLevel(DataAccessException.class)
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;
    private final AtomicLong nextNoteId = new AtomicLong(1L);

    @Override
    @Transactional
    @CatchThrows(
	call = "newNoteModifyingException",
	args = "noteId")
    public PresenceDto delete(long noteId) {
	return PresenceDto
		   .wrap(noteRepository.existsById(noteId))
		   .ifPresent(() -> noteRepository.deleteById(noteId));
    }

    @Override
    @CatchThrows(
	call = "newNoteAlreadyPresentException")
    public NoteDto save(UpdateNoteDto dto) {
	long id = nextNoteId.getAndIncrement();
	Note entity = noteMapper.toEntity(id, dto);
	Note saved = noteRepository.save(entity);
	return noteMapper.toDto(saved);
    }

    @Override
    public List<NoteDto> getAllByNewsId(long newsId) {
	List<Note> entities = noteRepository.findAllByNewsId(newsId);
	return noteMapper.toDtoList(entities);
    }

    @Override
    @Transactional
    @CatchThrows(
	call = "newNoteModifyingException",
	args = "noteId")
    public NoteDto update(long noteId, UpdateNoteDto dto) {
	Note entity = noteRepository
			  .findById(noteId)
			  .orElseThrow(() -> newNoteNotFountException(noteId));
	long id = nextNoteId.getAndIncrement();
	Note _ = noteMapper.partialUpdate(entity, id, dto);
	Note saved = noteRepository.save(entity);
	return noteMapper.toDto(saved);
    }

    @Override
    public NoteDto getById(long noteId) {
	Note entity = noteRepository
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
