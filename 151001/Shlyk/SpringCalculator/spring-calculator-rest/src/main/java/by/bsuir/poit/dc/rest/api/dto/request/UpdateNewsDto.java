package by.bsuir.poit.dc.rest.api.dto.request;

import by.bsuir.poit.dc.rest.api.dto.groups.Create;
import by.bsuir.poit.dc.rest.api.dto.groups.Update;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link by.bsuir.poit.dc.rest.model.News}
 */
public record UpdateNewsDto(
    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    Long id,
    @Size(min = 2, max = 64)
    @NotNull(groups = Create.class)
    String title,

    @Size(min = 4, max = 2048)
    @NotNull(groups = Create.class)
    String content,
    Long userId

) implements Serializable {
}