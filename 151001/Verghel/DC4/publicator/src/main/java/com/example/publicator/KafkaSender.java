package com.example.publicator;

import com.example.publicator.dto.MessageRequestTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {
    @Autowired
    private KafkaTemplate<String, MessageRequestTo> kafkaTemplate;

    public void sendMessage(MessageRequestTo message, String topicName) {
        kafkaTemplate.send(topicName, message);
    }
}
