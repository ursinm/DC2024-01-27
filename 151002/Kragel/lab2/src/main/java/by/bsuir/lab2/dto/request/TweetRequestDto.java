package by.bsuir.lab2.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TweetRequestDto(
        Long id,

        @NotNull
        Long creatorId,

        @NotBlank
        @Size(min = 2, max = 64)
        String title,

        @NotBlank
        @Size(min = 4, max = 2048)
        String content
) {
}
