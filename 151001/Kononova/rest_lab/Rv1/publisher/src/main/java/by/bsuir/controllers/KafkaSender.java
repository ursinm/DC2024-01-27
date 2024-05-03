package by.bsuir.controllers;

import by.bsuir.dto.MessageRequestTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {

    @Autowired
    private KafkaTemplate<String, MessageRequestTo> userKafkaTemplate;

    void sendCustomMessage(MessageRequestTo messageRequestTo, String topicName) {
        userKafkaTemplate.send(topicName, messageRequestTo);
    }
}
