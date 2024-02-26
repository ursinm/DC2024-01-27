package by.bsuir.taskrest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatorRequestTo(
        Long id,
        @NotNull @Size(min = 5, max = 32)
        String login,
        @NotNull @Size(min = 8, max = 32)
        String password,
        @NotBlank
        String firstName,
        @NotBlank
        String lastName
) {
}
