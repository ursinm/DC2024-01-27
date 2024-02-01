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

import java.util.List;

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
	return new ResourceNotFoundException(msg, 45);

    }
}
