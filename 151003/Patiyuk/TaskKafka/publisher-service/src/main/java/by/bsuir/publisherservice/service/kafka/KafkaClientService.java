package by.bsuir.publisherservice.service.kafka;

import by.bsuir.publisherservice.dto.message.TopicMessage;
import by.bsuir.publisherservice.exception.ExchangeTimeoutException;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class KafkaClientService {
    private final KafkaTemplate<String, TopicMessage> kafkaTemplate;
    private final ConcurrentHashMap<String, Exchanger<TopicMessage>> cache = new ConcurrentHashMap<>();

    public void sendMessage(TopicMessage message, String topic) {
        kafkaTemplate.send(topic, createId(), message);
    }

    public TopicMessage sendSyncMessage(TopicMessage message, String topic) {
        String id = createId();
        Exchanger<TopicMessage> exchanger = new Exchanger<>();
        cache.put(id, exchanger);
        try {
            kafkaTemplate.send(topic, id, message);
            return exchanger.exchange(null, 1, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            cache.remove(id);
            throw new ExchangeTimeoutException("Timeout while waiting for response");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ExchangeTimeoutException("Interrupted while waiting for response");
        }
    }

    @KafkaListener(topics = "${spring.kafka.topic.response}", groupId = "group_id-#{T(java.util.UUID).randomUUID().toString()}")
    private void listen(ConsumerRecord<String, TopicMessage> response) throws InterruptedException, TimeoutException {
        String key = response.key();
        TopicMessage message = response.value();
        Exchanger<TopicMessage> exchanger = cache.remove(key);
        if (exchanger != null) {
            exchanger.exchange(message, 1, TimeUnit.SECONDS);
        }
    }

    private static String createId() {
        return UUID.randomUUID().toString();
    }
}
