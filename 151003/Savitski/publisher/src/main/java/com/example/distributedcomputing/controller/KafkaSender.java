package com.example.distributedcomputing.controller;

import com.example.distributedcomputing.model.request.NoteRequestTo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaSender {
    private  final KafkaTemplate<String, NoteRequestTo> userKafkaTemplate;


    void sendCustomMessage(NoteRequestTo commentRequestTo, String topicName) {
        userKafkaTemplate.send(topicName, commentRequestTo);
    }
}