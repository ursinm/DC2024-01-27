package discussion.controllers;

import discussion.dto.NoteResponseTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {
    @Autowired
    private KafkaTemplate<String, NoteResponseTo> kafkaTemplate;


    void sendCustomMessage(NoteResponseTo noteResponseTo, String topicName) {
        kafkaTemplate.send(topicName, noteResponseTo);
    }
}
