package com.example.discussion.controller;

import com.example.discussion.dto.MessageRequestTo;
import com.example.discussion.dto.MessageResponseTo;
import com.example.discussion.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/")
public class MessageController {
    @Autowired
    MessageService messageService;

    @PostMapping(value = "messages")
    public ResponseEntity<?> create(@RequestHeader("Accept-Language") String acceptLanguageHeader, @RequestBody MessageRequestTo messageRequestTo) {
        MessageResponseTo message = messageService.create(messageRequestTo, acceptLanguageHeader);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping(value = "messages", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> read()
    {
        final List<MessageResponseTo> list = messageService.readAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "messages/{id}")
    public ResponseEntity<?> read(@PathVariable(name = "id") int id) {
        MessageResponseTo message = messageService.read(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping(value = "messages")
    public ResponseEntity<?> update(@RequestHeader("Accept-Language") String acceptLanguageHeader, @RequestBody MessageRequestTo messageRequestTo) {
        MessageResponseTo message = messageService.update(messageRequestTo, acceptLanguageHeader);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping(value = "messages/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        boolean isDeleted = messageService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
