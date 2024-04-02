package by.bsuir.poit.dc.rest.services;

import by.bsuir.poit.dc.rest.api.dto.request.UpdateNoteDto;
import by.bsuir.poit.dc.rest.api.dto.response.PresenceDto;
import by.bsuir.poit.dc.rest.api.dto.response.NoteDto;
import jakarta.validation.Valid;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 31/01/2024
 */
public interface NoteService {

    NoteDto update(long noteId, @Valid UpdateNoteDto dto);

    NoteDto getById(long noteId);

    @Deprecated
    List<NoteDto> getAll();

    PresenceDto delete(long noteId);

}
