package com.example.rw.controller;

import com.example.rw.kafkacl.listener.MessageKafkaListener;
import com.example.rw.kafkacl.request.KafkaRequest;
import com.example.rw.kafkacl.request.KafkaRequestType;
import com.example.rw.kafkacl.response.KafkaResponse;
import com.example.rw.model.dto.message.MessageRequestTo;
import com.example.rw.model.dto.message.MessageResponseTo;
import com.example.rw.service.dto_converter.interfaces.MessageToConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("${api.request.prefix}/messages")
@RequiredArgsConstructor
@Slf4j
public class MessageController {
    private final MessageKafkaListener messageKafkaListener;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MessageToConverter messageToConverter;

    @PostMapping()
    public ResponseEntity<?> createMessage(@RequestBody @Valid MessageRequestTo messageRequestTo) throws JsonProcessingException {
        KafkaRequest request = new KafkaRequest(KafkaRequestType.POST, List.of(objectMapper.writeValueAsString(messageRequestTo)));
        kafkaTemplate.send("inTopic", objectMapper.writeValueAsString(request));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException interruptedException){
            log.warn("Interrupted");
        }
        KafkaResponse response = messageKafkaListener.getResponses().get(KafkaRequestType.POST);
        return ResponseEntity.status(response.getStatus()).body(response.getResponseObject());
    }

    @GetMapping()
    public ResponseEntity<List<MessageResponseTo>> receiveAllMessages() throws JsonProcessingException{
        KafkaRequest request = new KafkaRequest(KafkaRequestType.GET_ALL, Collections.emptyList());
        kafkaTemplate.send("inTopic", objectMapper.writeValueAsString(request));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException interruptedException){
            log.warn("Interrupted");
        }
        KafkaResponse response = messageKafkaListener.getResponses().get(KafkaRequestType.GET_ALL);
        List<MessageResponseTo> list = objectMapper.readValue(response.getResponseObject(), new TypeReference<List<MessageResponseTo>>() {});
        return ResponseEntity.status(200).body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponseTo> receiveMessageById(@PathVariable Long id) throws JsonProcessingException{
        KafkaRequest request = new KafkaRequest(KafkaRequestType.GET, List.of(objectMapper.writeValueAsString(id)));
        kafkaTemplate.send("inTopic", objectMapper.writeValueAsString(request));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException interruptedException){
            log.warn("Interrupted");
        }
        KafkaResponse response = messageKafkaListener.getResponses().get(KafkaRequestType.GET);
        MessageResponseTo responseTo = objectMapper.readValue(response.getResponseObject(),MessageResponseTo.class);
        return ResponseEntity.status(response.getStatus()).body(responseTo);
    }

    @PutMapping()
    public ResponseEntity<MessageResponseTo> updateMessage(@RequestBody @Valid MessageRequestTo messageRequestTo) throws JsonProcessingException{
        KafkaRequest request = new KafkaRequest(KafkaRequestType.PUT, List.of(objectMapper.writeValueAsString(messageRequestTo)));
        kafkaTemplate.send("inTopic", objectMapper.writeValueAsString(request));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException interruptedException){
            log.warn("Interrupted");
        }
        KafkaResponse response = messageKafkaListener.getResponses().get(KafkaRequestType.PUT);
        MessageResponseTo responseTo = objectMapper.readValue(response.getResponseObject(), MessageResponseTo.class);
        return ResponseEntity.status(response.getStatus()).body(responseTo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessageById(@PathVariable Long id) throws JsonProcessingException{
        KafkaRequest request = new KafkaRequest(KafkaRequestType.DELETE, List.of(objectMapper.writeValueAsString(id)));
        kafkaTemplate.send("inTopic", objectMapper.writeValueAsString(request));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException interruptedException){
            log.warn("Interrupted");
        }
        KafkaResponse response = messageKafkaListener.getResponses().get(KafkaRequestType.DELETE);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
