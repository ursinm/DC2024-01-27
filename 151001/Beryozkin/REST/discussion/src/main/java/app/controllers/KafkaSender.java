package app.controllers;

import app.dto.MessageResponseTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {
    @Autowired
    private KafkaTemplate<String, MessageResponseTo> userKafkaTemplate;


    void sendCustomMessage(MessageResponseTo messageResponseTo, String topicName) {
        userKafkaTemplate.send(topicName, messageResponseTo);
    }
}
