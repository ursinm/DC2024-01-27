package by.bsuir.poit.dc.rest.services.impl;

import by.bsuir.poit.dc.context.CatchLevel;
import by.bsuir.poit.dc.context.CatchThrows;
import by.bsuir.poit.dc.rest.api.dto.mappers.NoteMapper;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateNoteDto;
import by.bsuir.poit.dc.rest.api.dto.response.NoteDto;
import by.bsuir.poit.dc.rest.api.dto.response.PresenceDto;
import by.bsuir.poit.dc.rest.api.exceptions.ResourceModifyingException;
import by.bsuir.poit.dc.rest.api.exceptions.ResourceNotFoundException;
import by.bsuir.poit.dc.rest.dao.NoteRepository;
import by.bsuir.poit.dc.rest.model.Note;
import by.bsuir.poit.dc.rest.services.NoteService;
import com.google.errorprone.annotations.Keep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 31/01/2024
 */

@Slf4j
@Service
@CatchLevel(DataAccessException.class)
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    @Override
    @Transactional
    @CatchThrows(
	call = "newNoteModifyingException",
	args = {"noteId"})
    public NoteDto update(long noteId, UpdateNoteDto dto) {
	Note entity = noteRepository
			  .findById(noteId)
			  .orElseThrow(() -> newNoteNotFountException(noteId));
	Note updatedEntity = noteMapper.partialUpdate(entity, dto);
	Note savedEntity = noteRepository.save(updatedEntity);
	return noteMapper.toDto(savedEntity);
    }

    @Override
    public NoteDto getById(long noteId) {
	return noteRepository
		   .findById(noteId)
		   .map(noteMapper::toDto)
		   .orElseThrow(() -> newNoteNotFountException(noteId));
    }

    @Override
    public List<NoteDto> getAll() {
	return noteRepository.findAll().stream()
		   .map(noteMapper::toDto)
		   .toList();
    }

    @Override
    @Transactional
    public PresenceDto delete(long noteId) {
	return PresenceDto
		   .wrap(noteRepository.existsById(noteId))
		   .ifPresent(() -> noteRepository.deleteById(noteId));
    }

    @Keep
    private static ResourceModifyingException newNoteModifyingException(
	long noteId,
	Throwable cause
    ) {
	final String msg = STR."Failed to modify note by id =\{noteId}";
	log.warn(msg);
	return new ResourceModifyingException(msg, 70);
    }

    @Keep

    private static ResourceNotFoundException newNoteNotFountException(long noteId) {
	final String msg = STR."Failed to find news' note by id = \{noteId}";
	log.warn(msg);
	return new ResourceNotFoundException(msg, 45);

    }
}
