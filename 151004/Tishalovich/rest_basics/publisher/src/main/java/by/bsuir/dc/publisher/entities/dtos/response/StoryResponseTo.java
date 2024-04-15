package by.bsuir.dc.publisher.entities.dtos.response;

import java.util.Date;

public record StoryResponseTo(
        Long id,
        Long authorId,
        String title,
        String content,
        Date created,
        Date modified) {
}
