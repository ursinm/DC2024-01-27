package by.bsuir.poit.dc.cassandra.services;

import by.bsuir.poit.dc.cassandra.api.dto.request.UpdateNoteDto;
import by.bsuir.poit.dc.cassandra.api.dto.response.NoteDto;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 06/03/2024
 */
public interface NoteService {
    NoteDto save(UpdateNoteDto dto);

    NoteDto update(long noteId, UpdateNoteDto dto);

    boolean delete(long noteId);

    NoteDto getById(long noteId);

    List<NoteDto> getAllByNewsId(long newsId);
}
