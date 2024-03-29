package by.rusakovich.newsdistributedsystem.model.dto.author;

import jakarta.validation.constraints.Size;

public record AuthorRequestTO(
        Long id,
        @Size(min = 2, max = 64) String login,
        @Size(min = 8, max = 128) String password,
        @Size(min = 2, max = 64) String firstname,
        @Size(min = 2, max = 64) String lastname
) {
}
