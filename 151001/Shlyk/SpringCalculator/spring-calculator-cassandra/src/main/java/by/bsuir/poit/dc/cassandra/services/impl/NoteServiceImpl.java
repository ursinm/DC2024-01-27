package by.bsuir.poit.dc.cassandra.services.impl;

import by.bsuir.poit.dc.cassandra.api.dto.request.UpdateNoteDto;
import by.bsuir.poit.dc.cassandra.api.dto.response.NoteDto;
import by.bsuir.poit.dc.cassandra.dao.NoteRepository;
import by.bsuir.poit.dc.cassandra.services.NoteService;
import by.bsuir.poit.dc.context.CatchLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Override
    public boolean delete(long noteId) {
	return false;
    }

    @Override
    public NoteDto save(UpdateNoteDto dto) {
	return null;
    }

    @Override
    public List<NoteDto> getAllByNewsId(long newsId) {
	return null;
    }

    @Override
    public NoteDto update(long noteId, UpdateNoteDto dto) {
	return null;
    }

    @Override
    public NoteDto getById(long noteId) {
	return null;
    }
}
