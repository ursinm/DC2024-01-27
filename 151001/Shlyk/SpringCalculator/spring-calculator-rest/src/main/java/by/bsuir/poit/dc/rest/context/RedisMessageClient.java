package by.bsuir.poit.dc.rest.context;

import by.bsuir.poit.dc.rest.api.dto.request.UpdateNoteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;

/**
 * @author Paval Shlyk
 * @since 06/04/2024
 */
//@Component
@RequiredArgsConstructor
public class RedisMessageClient {
    private final ReactiveRedisTemplate<String, UpdateNoteDto> redisTemplate;
    private final String redisKey = "publisher:message:response";
//    public void doSutff() {
//	redisTemplate.opsForHash()
//    }
}
