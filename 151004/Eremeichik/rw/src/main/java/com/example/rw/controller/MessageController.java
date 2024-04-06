package com.example.rw.controller;

import com.example.rw.kafkacl.response.KafkaResponse;
import com.example.rw.model.dto.message.MessageRequestTo;
import com.example.rw.model.dto.message.MessageResponseTo;
import com.example.rw.service.db_operations.interfaces.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.request.prefix}/messages")
@RequiredArgsConstructor
@Slf4j
public class MessageController {
    private final MessageService messageService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping()
    public ResponseEntity<?> createMessage(@RequestBody @Valid MessageRequestTo messageRequestTo) throws JsonProcessingException {
        KafkaResponse response = messageService.save(messageRequestTo);
        return ResponseEntity.status(HttpStatus.CREATED).body(response.getResponseObject());
    }

    @GetMapping()
    public ResponseEntity<List<MessageResponseTo>> receiveAllMessages() throws JsonProcessingException{
        KafkaResponse response = messageService.findAll();
        List<MessageResponseTo> list = objectMapper.readValue(response.getResponseObject(), new TypeReference<List<MessageResponseTo>>() {});
        return ResponseEntity.status(200).body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponseTo> receiveMessageById(@PathVariable Long id) throws JsonProcessingException{
        KafkaResponse response = messageService.findById(id);
        MessageResponseTo responseTo = objectMapper.readValue(response.getResponseObject(),MessageResponseTo.class);
        return ResponseEntity.status(response.getStatus()).body(responseTo);
    }

    @PutMapping()
    public ResponseEntity<MessageResponseTo> updateMessage(@RequestBody @Valid MessageRequestTo messageRequestTo) throws JsonProcessingException{
        KafkaResponse response = messageService.update(messageRequestTo);
        MessageResponseTo responseTo = objectMapper.readValue(response.getResponseObject(), MessageResponseTo.class);
        return ResponseEntity.status(response.getStatus()).body(responseTo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessageById(@PathVariable Long id) throws JsonProcessingException{
        messageService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
