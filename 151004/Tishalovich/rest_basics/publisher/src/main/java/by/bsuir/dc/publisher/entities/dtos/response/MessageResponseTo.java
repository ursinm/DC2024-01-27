package by.bsuir.dc.publisher.entities.dtos.response;

import org.springframework.data.redis.core.RedisHash;
import java.io.Serializable;

@RedisHash("message")
public record MessageResponseTo(
        Long id,
        Long storyId,
        String country,
        String content) implements Serializable {
}
