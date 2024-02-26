package by.bsuir.taskrest.dto.request;

import jakarta.validation.constraints.NotBlank;

public record MessageRequestTo(
        Long id,
        Long storyId,
        @NotBlank
        String content
) {
}
