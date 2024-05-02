package org.example.publisher.api.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.example.publisher.impl.post.dto.PostResponseTo;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


import java.util.concurrent.CountDownLatch;

@Service
public class PostConsumer {
    private static final String TOPIC_NAME = "InTopic";

    private CountDownLatch latch;

    @Getter
    private PostResponseTo response;
    private String requestId;

    @KafkaListener(topics = TOPIC_NAME, groupId = "group")
    public void listen(String message) throws JsonProcessingException {
        if (message.startsWith("requestId="+requestId)) {
            ObjectMapper mapper = new ObjectMapper();
            this.response = mapper.readValue(message.substring(message.indexOf("{")), PostResponseTo.class);
            latch.countDown();
        }
    }

    public void subscribeToResponse(CountDownLatch latch, String requestId) {
        this.latch = latch;
        this.requestId = requestId;
    }

}
