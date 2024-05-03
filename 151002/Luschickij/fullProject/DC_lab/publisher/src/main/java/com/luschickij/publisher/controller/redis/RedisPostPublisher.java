package com.luschickij.publisher.controller.redis;

import com.luschickij.publisher.dto.KafkaPostResponseTo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class RedisPostPublisher implements PostPublisher<KafkaPostResponseTo> {

    RedisTemplate<String, KafkaPostResponseTo> redisTemplate;

    private ChannelTopic topic;

    public void publish(KafkaPostResponseTo post) {
        log.info("REDIS: Post sent: {}", post);
        redisTemplate.convertAndSend(topic.getTopic(), post);
    }
}
