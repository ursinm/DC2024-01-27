package com.example.discussion;

import com.example.discussion.dto.MessageResponseTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {
    @Autowired
    private KafkaTemplate<String, MessageResponseTo> kafkaTemplate;

    public void sendMessage(MessageResponseTo message, String topicName) {
        kafkaTemplate.send(topicName, message);
    }
}
