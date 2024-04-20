package by.bsuir.dc.publisher.entities.dtos.response;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("author")
public record AuthorResponseTo (
    Long id,
    String login,
    String firstname,
    String lastname) implements Serializable {
}
