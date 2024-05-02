package com.luschickij.publisher.service.redis;

import com.luschickij.publisher.dto.post.PostResponseTo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class RedisClientCacheService implements RedisCacheService<Long, PostResponseTo> {

    private static final String REDIS_PUBLISHER_KEY = "publisher:post:response";
    RedisTemplate<String, PostResponseTo> redisTemplate;

    @Override
    public PostResponseTo get(Long key) {
        log.info("Get from redis: {}", key);
        return (PostResponseTo) redisTemplate.opsForHash().get(REDIS_PUBLISHER_KEY, key);
    }

    @Override
    public void put(Long key, PostResponseTo value) {
        log.info("Put to redis key: {}, value: {}", key, value);
        redisTemplate.opsForHash().put(REDIS_PUBLISHER_KEY, key, value);
    }

    @Override
    public void delete(Long key) {
        log.info("Delete from redis: {}", key);
        redisTemplate.opsForHash().delete(REDIS_PUBLISHER_KEY, key);
    }
}
