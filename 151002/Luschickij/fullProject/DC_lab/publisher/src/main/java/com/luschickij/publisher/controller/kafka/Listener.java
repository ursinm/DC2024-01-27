package com.luschickij.publisher.controller.kafka;

import com.luschickij.publisher.controller.redis.RedisPostPublisher;
import com.luschickij.publisher.dto.KafkaPostResponseTo;
import com.luschickij.publisher.service.kafka.KafkaSendReceiver;
import com.luschickij.publisher.service.kafka.KafkaSendReceiverMap;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@AllArgsConstructor
@Component
@Slf4j
public class Listener {

    private final KafkaSendReceiverMap<UUID> sendReceiverMap;

    private final RedisPostPublisher redisPostPublisher;


    @KafkaListener(topics = "${kafka.topic.post.response}")
    public void postListener(KafkaPostResponseTo response) {

        log.info("KAFKA: Post received: {}", response);

        UUID rqId = response.getRequestId();
        if (rqId != null) {
            synchronized (sendReceiverMap) {
                if (sendReceiverMap.containsKey(rqId)) {
                    KafkaSendReceiver<KafkaPostResponseTo> stringSenderReceiver = (KafkaSendReceiver<KafkaPostResponseTo>) sendReceiverMap.get(rqId);
                    stringSenderReceiver.send(response);
                } else {
                    redisPostPublisher.publish(response);
                }
            }
        }
    }
}
