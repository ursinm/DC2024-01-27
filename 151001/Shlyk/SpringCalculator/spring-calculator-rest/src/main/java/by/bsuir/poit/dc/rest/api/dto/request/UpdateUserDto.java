package by.bsuir.poit.dc.rest.api.dto.request;

import by.bsuir.poit.dc.rest.api.dto.groups.Create;
import by.bsuir.poit.dc.rest.model.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
public record UpdateUserDto(
    @Size(min = 2, max = 64)
    @NotNull(groups = Create.class)
    String login,
    @Size(min = 8, max = 128)
    @NotNull(groups = Create.class)
    String password,
    @Size(min = 2, max = 64)
    @NotNull(groups = Create.class)
    String firstName,
    @Size(min = 2, max = 64)
    @NotNull(groups = Create.class)
    String lastName
) implements Serializable {
}