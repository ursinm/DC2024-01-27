package com.example.publisher.kafka;

import com.example.publisher.event.*;
import com.example.publisher.service.exceptions.ResourceException;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeoutException;

@Component
@RequiredArgsConstructor
public class KafkaMessageClient {
    public static final String IN_TOPIC = "InTopic";
    public static final String OUT_TOPIC = "OutTopic";

    private final KafkaTemplate<String, InTopicEvent> kafkaTemplate;
    private final ConcurrentHashMap<UUID, Exchanger<Exchangeable>> kafkaExchangers =
            new ConcurrentHashMap<>();


    private static long hashMessage(InTopicMessage message) {
        if (message == null)
            return 0;
        final MessageInTopicTo dto = message.commentDto();
        if (dto == null)
            return 0;
        final Long issueId = dto.getIssueId();
        if (issueId == null)
            return 0;
        return issueId;
    }

    public OutTopicMessage sync(InTopicMessage message) {
        final UUID id = UUID.randomUUID();
        final InTopicEvent inTopicEvent = new InTopicEvent(id, message);
        Exchanger<Exchangeable> exchanger = new Exchanger<>();
        kafkaExchangers.put(id, exchanger);
        try {
            kafkaTemplate.send(IN_TOPIC, Long.toString(hashMessage(message)), inTopicEvent);
            Exchangeable result = exchanger.exchange(inTopicEvent, 1, TimeUnit.SECONDS);
            if (result instanceof OutTopicEvent outEvent) {
                return outEvent.message();
            } else {
                throw new ResourceException(400, "Invalid comment response data");
            }
        } catch (TimeoutException ex) {
            kafkaExchangers.remove(id);
            throw new ResourceException(400,"Response time has expired");
        } catch (InterruptedException ex) {
            throw new ResourceException(400,"Comment data request failed");
        }
    }

    @KafkaListener(topics = OUT_TOPIC, groupId = "group-id=#{T(java.util.UUID).randomUUID().toString()}")
    private void listen(ConsumerRecord<String, OutTopicEvent> record) {
        final OutTopicEvent event = record.value();
        final UUID key = event.id();
        Exchanger<Exchangeable> exchanger = kafkaExchangers.remove(key);
        if (exchanger != null) {
            try {
                exchanger.exchange(event);
            } catch (InterruptedException ex) {
                throw new ResourceException(400, "Comment data request failed");
            }
        }
    }
}