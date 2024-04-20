package by.bsuir.publisher.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record IssueRequestDto(
        Long id,

        @NotNull
        Long authorId,

        @NotBlank
        @Size(min = 2, max = 64)
        String title,

        @NotBlank
        @Size(min = 4, max = 2048)
        String content
) {
}
