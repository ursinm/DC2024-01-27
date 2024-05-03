package org.example.publisher.api.kafka.producer;

import org.example.publisher.api.kafka.consumer.PostConsumer;
import org.example.publisher.impl.post.dto.PostResponseTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class PostProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "OutTopic";
    private final PostConsumer postConsumer;

    @Autowired
    public PostProducer(KafkaTemplate<String, String> kafkaTemplate, PostConsumer postConsumer) {
        this.kafkaTemplate = kafkaTemplate;
        this.postConsumer = postConsumer;
    }

    public PostResponseTo sendPost(String method, String message, boolean isShouldWait) throws InterruptedException {
        UUID requestId = UUID.randomUUID();
        kafkaTemplate.send(TOPIC, "requestId=" + requestId + "," + method + ":" + message);
        if (isShouldWait) {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            postConsumer.subscribeToResponse(countDownLatch, requestId.toString());
            if (countDownLatch.await(10, TimeUnit.SECONDS)) {
                return postConsumer.getResponse();
            } else {
                throw new RuntimeException("Timeout exceed");
            }
        } else {
            return null;
        }
    }
}

