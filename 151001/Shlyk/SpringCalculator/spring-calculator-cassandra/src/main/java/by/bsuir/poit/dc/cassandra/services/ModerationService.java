package by.bsuir.poit.dc.cassandra.services;

import by.bsuir.poit.dc.cassandra.api.dto.request.UpdateNoteDto;
import jakarta.validation.constraints.NotNull;

/**
 * @author Paval Shlyk
 * @since 08/04/2024
 */
public interface ModerationService {
    ModerationResult verify(@NotNull String content);
    UpdateNoteDto prepareUpdate(@NotNull UpdateNoteDto dto);
    UpdateNoteDto prepareSave(@NotNull UpdateNoteDto dto);
}
