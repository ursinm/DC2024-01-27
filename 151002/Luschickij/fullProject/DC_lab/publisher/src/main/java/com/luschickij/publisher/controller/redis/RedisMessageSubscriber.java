package com.luschickij.publisher.controller.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luschickij.publisher.dto.KafkaPostResponseTo;
import com.luschickij.publisher.service.kafka.KafkaSendReceiver;
import com.luschickij.publisher.service.kafka.KafkaSendReceiverMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class RedisMessageSubscriber implements MessageListener {
    private final KafkaSendReceiverMap<UUID> sendReceiverMap;
    private final ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {

        try {
            KafkaPostResponseTo response = objectMapper.readValue(message.toString(), KafkaPostResponseTo.class);
            log.info("REDIS: Post received: {}", response);
            UUID rqId = response.getRequestId();
            synchronized (sendReceiverMap) {
                if (sendReceiverMap.containsKey(rqId)) {
                    KafkaSendReceiver<KafkaPostResponseTo> stringSenderReceiver = (KafkaSendReceiver<KafkaPostResponseTo>) sendReceiverMap.get(rqId);
                    stringSenderReceiver.send(response);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
