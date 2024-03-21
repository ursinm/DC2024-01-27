package by.bsuir.poit.dc.rest.api.dto.request;

import by.bsuir.poit.dc.rest.api.dto.groups.Create;
import by.bsuir.poit.dc.rest.api.dto.groups.Update;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link by.bsuir.poit.dc.rest.model.Note}
 */
public record UpdateNoteDto(
    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    Long id,
    @NotNull(groups = Create.class)
    Long newsId,
    @Size(min = 2, max = 2048)
    @NotNull(groups = Create.class)
    String content
) implements Serializable {
}