package by.bsuir.newsapi.model.response;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public record NewsResponseTo(
        Long id,
        Long editorId,
        String title,
        String content,
        LocalDateTime created,
        LocalDateTime modified
) {}
