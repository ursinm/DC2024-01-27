package by.bsuir.dc.rest_basics.entities.dtos.request;

import java.util.Date;

public record StoryRequestTo(
        Long id,
        Long authorId,
        String title,
        String content,
        Date created,
        Date modified) {
}
