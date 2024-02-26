package by.bsuir.taskrest.dto.request;

import jakarta.validation.constraints.NotBlank;

public record StoryRequestTo(
        Long id,
        Long creatorId,
        @NotBlank
        String title,
        @NotBlank
        String content
) {
}
