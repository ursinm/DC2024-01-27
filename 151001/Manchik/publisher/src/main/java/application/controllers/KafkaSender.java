package application.controllers;

import application.dto.NoteRequestTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {
    @Autowired
    private KafkaTemplate<String, NoteRequestTo> kafkaTemplate;


    void sendCustomMessage(NoteRequestTo commentRequestTo, String topicName) {
        kafkaTemplate.send(topicName, commentRequestTo);
    }
}
