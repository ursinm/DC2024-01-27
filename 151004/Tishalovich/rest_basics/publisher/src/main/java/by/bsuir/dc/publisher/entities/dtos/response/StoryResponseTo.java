package by.bsuir.dc.publisher.entities.dtos.response;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Date;

@RedisHash("story")
public record StoryResponseTo(
        Long id,
        Long authorId,
        String title,
        String content,
        Date created,
        Date modified) implements Serializable {
}
