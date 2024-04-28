package by.bsuir.publisher.model.response;

import java.sql.Date;
import java.time.LocalDateTime;

public record NewsResponseTo(
        Long id,
        Long editorId,
        String title,
        String content,
        Date created,
        Date modified
) {}
