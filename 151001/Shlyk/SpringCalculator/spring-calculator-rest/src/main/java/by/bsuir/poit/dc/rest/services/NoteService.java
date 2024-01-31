package by.bsuir.poit.dc.rest.services;

import by.bsuir.poit.dc.rest.api.dto.request.UpdateNoteDto;
import by.bsuir.poit.dc.rest.api.dto.response.NoteDto;
import jakarta.validation.Valid;

/**
 * @author Paval Shlyk
 * @since 31/01/2024
 */
public interface NoteService {
    void update(long noteId, @Valid UpdateNoteDto dto);

    NoteDto getById(long noteId);

    boolean delete(long noteId);
}
