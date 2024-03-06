package by.bsuir.poit.dc.cassandra.api.dto.request;

import by.bsuir.poit.dc.dto.groups.Create;
import by.bsuir.poit.dc.dto.groups.Update;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

/**
 * @author Paval Shlyk
 * @since 06/03/2024
 */
public record UpdateNoteDto(
    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    Long id,
    Long newsId,
    String country,
    String content
) {
}
