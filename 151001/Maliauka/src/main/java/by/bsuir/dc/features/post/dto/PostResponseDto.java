package by.bsuir.dc.features.post.dto;

import by.bsuir.dc.features.post.Post;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link Post}
 */
public record PostResponseDto(
        @NotBlank @Size(min = 2, max = 2048) String content,
        @Min(1) @Max(Long.MAX_VALUE) Long newsId
) implements Serializable {}