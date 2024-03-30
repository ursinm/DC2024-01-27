package com.example.rw.controller;

import com.example.rw.model.dto.message.MessageRequestTo;
import com.example.rw.model.dto.message.MessageResponseTo;
import com.example.rw.service.dto_converter.interfaces.MessageToConverter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    private final MessageToConverter messageToConverter;

    @PostMapping()
    public ResponseEntity<MessageResponseTo> createMessage(@RequestBody @Valid MessageRequestTo messageRequestTo) {
        return null;
    }

    @GetMapping()
    public ResponseEntity<List<MessageResponseTo>> receiveAllMessages() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponseTo> receiveMessageById(@PathVariable Long id) {
        return null;
    }

    @PutMapping()
    public ResponseEntity<MessageResponseTo> updateMessage(@RequestBody @Valid MessageRequestTo messageRequestTo) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessageById(@PathVariable Long id) {
        return null;
    }
}
