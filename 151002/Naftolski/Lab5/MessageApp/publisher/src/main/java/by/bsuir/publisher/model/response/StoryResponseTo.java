package by.bsuir.publisher.model.response;

import java.sql.Date;
import java.time.LocalDateTime;

public record StoryResponseTo(
        Long id,
        Long creatorId,
        String title,
        String content,
        Date created,
        Date modified
) {
}
