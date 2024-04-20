package by.bsuir.discussion.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostRequestDto(
        Long id,

        @NotNull
        Long issueId,

        @NotBlank
        @Size(min = 2, max = 2048)
        String content
) {
}
