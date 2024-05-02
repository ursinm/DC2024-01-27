package by.bsuir.poit.dc.rest.api.dto.request;

import by.bsuir.poit.dc.dto.groups.Create;
import jakarta.validation.constraints.NotNull;

/**
 * @author Paval Shlyk
 * @since 31/01/2024
 */
public record UpdateNewsLabelDto(
    @NotNull(groups = Create.class)
    Long labelId
) {
}
