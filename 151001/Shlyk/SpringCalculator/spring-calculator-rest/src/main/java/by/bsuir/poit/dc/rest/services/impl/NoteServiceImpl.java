package by.bsuir.poit.dc.rest.services.impl;

import by.bsuir.poit.dc.rest.api.dto.mappers.NoteMapper;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateNoteDto;
import by.bsuir.poit.dc.rest.api.dto.response.NoteDto;
import by.bsuir.poit.dc.rest.api.exceptions.ResourceNotFoundException;
import by.bsuir.poit.dc.rest.dao.NoteRepository;
import by.bsuir.poit.dc.rest.model.Note;
import by.bsuir.poit.dc.rest.services.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Paval Shlyk
 * @since 31/01/2024
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    @Override
    @Transactional
    public void update(long noteId, UpdateNoteDto dto) {
	Note entity = noteRepository
			  .findById(noteId)
			  .orElseThrow(() -> newNoteNotFountException(noteId));
	Note updatedEntity = noteMapper.partialUpdate(entity, dto);
	Note _ = noteRepository.save(updatedEntity);
    }

    @Override
    public NoteDto getById(long noteId) {
	return noteRepository
		   .findById(noteId)
		   .map(noteMapper::toDto)
		   .orElseThrow(() -> newNoteNotFountException(noteId));
    }

    @Override
    @Transactional
    public boolean delete(long noteId) {
	boolean isDeleted;
	if (noteRepository.existsById(noteId)) {
	    noteRepository.deleteById(noteId);
	    isDeleted = true;
	} else {
	    isDeleted = false;
	}
	return isDeleted;
    }

    private static ResourceNotFoundException newNoteNotFountException(long noteId) {
	final String msg = STR."Failed to find news' note by id = \{noteId}";
	log.warn(msg);
	return new ResourceNotFoundException(msg);

    }
}
