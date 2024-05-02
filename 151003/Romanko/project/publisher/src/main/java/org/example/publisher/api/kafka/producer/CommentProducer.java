package org.example.publisher.api.kafka.producer;

import org.example.publisher.api.kafka.consumer.CommentConsumer;
import org.example.publisher.impl.comment.dto.CommentResponseTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class CommentProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "OutTopic";
    private final CommentConsumer commentConsumer;

    @Autowired
    public CommentProducer(KafkaTemplate<String, String> kafkaTemplate, CommentConsumer commentConsumer) {
        this.kafkaTemplate = kafkaTemplate;
        this.commentConsumer = commentConsumer;
    }

    public CommentResponseTo sendComment(String method, String message, boolean isShouldWait) throws InterruptedException {
        UUID requestId = UUID.randomUUID();
        kafkaTemplate.send(TOPIC, "requestId=" + requestId + "," + method + ":" + message);
        if (isShouldWait) {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            commentConsumer.subscribeToResponse(countDownLatch, requestId.toString());
            if (countDownLatch.await(10, TimeUnit.SECONDS)) {
                return commentConsumer.getResponse();
            } else {
                throw new RuntimeException("Timeout exceed");
            }
        } else {
            return null;
        }
    }
}

