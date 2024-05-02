package by.bsuir.poit.dc.rest.services;

import by.bsuir.poit.dc.rest.api.dto.request.UpdateNoteDto;
import by.bsuir.poit.dc.rest.api.dto.response.PresenceDto;
import by.bsuir.poit.dc.rest.api.dto.response.NoteDto;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 31/01/2024
 */
public interface NoteService {
    @Deprecated
    List<NoteDto> getAll();

    NoteDto save(UpdateNoteDto dto, long newsId, @Nullable String language);

    NoteDto update(long noteId, @Valid UpdateNoteDto dto, @Nullable String language);

    PresenceDto delete(long noteId);

    NoteDto getById(long noteId);

    List<NoteDto> getAllByNewsId(long newsId);

}
