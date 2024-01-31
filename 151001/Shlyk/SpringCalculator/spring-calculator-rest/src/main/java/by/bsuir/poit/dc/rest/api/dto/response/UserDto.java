package by.bsuir.poit.dc.rest.api.dto.response;

import lombok.Builder;

import java.io.Serializable;

/**
 * DTO for {@link by.bsuir.poit.dc.rest.model.User}
 */
@Builder
public record UserDto(
    Long id,
    String login,
    String firstName,
    String lastName) implements Serializable {
}