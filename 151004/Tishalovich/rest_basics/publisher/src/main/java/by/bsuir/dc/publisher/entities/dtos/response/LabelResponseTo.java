package by.bsuir.dc.publisher.entities.dtos.response;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("label")
public record LabelResponseTo(
        Long id,
        String name) implements Serializable {
}
