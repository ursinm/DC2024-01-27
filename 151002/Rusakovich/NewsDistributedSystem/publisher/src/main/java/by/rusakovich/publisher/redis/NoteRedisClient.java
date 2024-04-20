package by.rusakovich.publisher.redis;

import by.rusakovich.publisher.model.dto.note.NoteResponseTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class NoteRedisClient {
    public static final String NOTE_REDIS_KEY = "publisher:note:response";
    private final RedisTemplate<String, NoteResponseTO> redisTemplate;

    public void put(NoteResponseTO noteResponseTO) {
        redisTemplate.opsForHash().put(NOTE_REDIS_KEY, noteResponseTO.id(), noteResponseTO);
        redisTemplate.expire(NOTE_REDIS_KEY, 10, TimeUnit.SECONDS);
    }
    public NoteResponseTO get(Long noteId) {
        return (NoteResponseTO) redisTemplate.opsForHash().get(NOTE_REDIS_KEY, noteId);
    }
    public void delete(Long noteId) {
        redisTemplate.opsForHash().delete(NOTE_REDIS_KEY, noteId);
    }

}
