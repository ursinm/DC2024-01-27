package com.example.discussion.kafka;

import com.example.discussion.model.response.NoteResponseTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {
    @Autowired
    private KafkaTemplate<String, NoteResponseTo> userKafkaTemplate;


    public void sendCustomMessage(NoteResponseTo commentResponseTo, String topicName) {
        userKafkaTemplate.send(topicName, commentResponseTo);
    }
}