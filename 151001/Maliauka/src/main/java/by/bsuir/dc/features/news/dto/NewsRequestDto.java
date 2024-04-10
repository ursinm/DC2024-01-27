package by.bsuir.dc.features.news.dto;

import by.bsuir.dc.features.news.News;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link News}
 */
public record NewsRequestDto(
        @NotBlank @Size(min = 2, max = 64) String title,
        @NotBlank @Size(min = 4, max = 2048) String content,
        @Past Instant created,
        @Past Instant modified,
        @Min(1) @Max(Long.MAX_VALUE) Long editorId
) implements Serializable {}