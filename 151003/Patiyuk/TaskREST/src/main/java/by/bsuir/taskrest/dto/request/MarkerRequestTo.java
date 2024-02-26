package by.bsuir.taskrest.dto.request;

import jakarta.validation.constraints.NotBlank;

public record MarkerRequestTo(
        Long id,
        @NotBlank
        String name
) {
}
