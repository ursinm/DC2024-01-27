package com.example.restapplication.controllers;

import com.example.restapplication.dto.MessageRequestTo;
import com.example.restapplication.dto.MessageResponseTo;
import com.example.restapplication.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/messages")
public class MessageController {
    @Autowired
    MessageService messageService;

    @GetMapping
    public ResponseEntity<List<MessageResponseTo>> getAll() {
        return ResponseEntity.status(200).body(messageService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponseTo> getById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(messageService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        messageService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<MessageResponseTo> save(@RequestBody MessageRequestTo message) {
        MessageResponseTo messageToSave = messageService.save(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(messageToSave);
    }

    @PutMapping()
    public ResponseEntity<MessageResponseTo> update(@RequestBody MessageRequestTo message) {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.update(message));
    }

    @GetMapping("/story/{id}")
    public ResponseEntity<MessageResponseTo> getByStoryId(@PathVariable Long id){
        return ResponseEntity.status(200).body(messageService.getByStoryId(id));
    }
}
