package by.bsuir.poit.dc.rest.api.dto.request;

import by.bsuir.poit.dc.rest.api.dto.groups.Create;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link by.bsuir.poit.dc.rest.model.Label}
 */
public record UpdateLabelDto(
    @Size(min = 2, max = 34)
    @NotNull(groups = Create.class)
    String name
) implements Serializable {
}