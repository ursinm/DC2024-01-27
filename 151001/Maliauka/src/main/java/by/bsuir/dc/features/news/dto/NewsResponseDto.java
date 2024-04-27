package by.bsuir.dc.features.news.dto;

import by.bsuir.dc.features.news.News;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * DTO for {@link News}
 */
public record NewsResponseDto(Long id, String title, String content, Instant created, Instant modified, List<Long> postIds,
                              Long editorId) implements Serializable {
}