package by.bsuir.dc.publisher.entities.dtos.request;

import java.util.Date;

public record StoryRequestTo(
        Long id,
        Long authorId,
        String title,
        String content,
        Date created,
        Date modified) {
}
