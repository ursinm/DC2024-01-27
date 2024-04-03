package com.example.restapplication.controllers;

import com.example.restapplication.dto.MessageRequestTo;
import com.example.restapplication.dto.MessageResponseTo;
import com.example.restapplication.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/messages")
public class MessageController {

    @Autowired
    MessageService messageService;

    @GetMapping
    public ResponseEntity<List<MessageResponseTo>> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortOrder)
    {
        return ResponseEntity.status(200).body(messageService.getAll(pageNumber, pageSize, sortBy, sortOrder));
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
    public ResponseEntity<List<MessageResponseTo>> getByStoryId(@PathVariable Long id){
        return ResponseEntity.status(200).body(messageService.getByStoryId(id));
    }
}
