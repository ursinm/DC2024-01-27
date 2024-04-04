package com.example.rw.controller;

import com.example.rw.exception.model.not_found.EntityNotFoundException;
import com.example.rw.model.dto.message.MessageRequestTo;
import com.example.rw.model.dto.message.MessageResponseTo;
import com.example.rw.model.entity.implementations.Message;
import com.example.rw.service.db_operations.interfaces.MessageService;
import com.example.rw.service.dto_converter.interfaces.MessageToConverter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
public class MessageController {

    private final MessageService messageService;
    private final MessageToConverter messageToConverter;

    @PostMapping()
    public ResponseEntity<MessageResponseTo> createMessage(@RequestBody @Valid MessageRequestTo messageRequestTo) {
        Message message = messageToConverter.convertToEntity(messageRequestTo);
        messageService.save(message);
        MessageResponseTo messageResponseTo = messageToConverter.convertToDto(message);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(messageResponseTo);
    }

    @GetMapping()
    public ResponseEntity<List<MessageResponseTo>> receiveAllMessages() {
        List<Message> messages = messageService.findAll();
        List<MessageResponseTo> responseList = messages.stream()
                .map(messageToConverter::convertToDto)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponseTo> receiveMessageById(@PathVariable Long id) {
        Message message = messageService.findById(id);
        MessageResponseTo messageResponseTo = messageToConverter.convertToDto(message);
        return ResponseEntity.ok(messageResponseTo);
    }

    @PutMapping()
    public ResponseEntity<MessageResponseTo> updateMessage(@RequestBody @Valid MessageRequestTo messageRequestTo) {
        Message message = messageToConverter.convertToEntity(messageRequestTo);
        messageService.save(message);
        MessageResponseTo messageResponseTo = messageToConverter.convertToDto(message);
        return ResponseEntity.ok(messageResponseTo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessageById(@PathVariable Long id) {
        try {
            messageService.deleteById(id);
        } catch (EntityNotFoundException entityNotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
