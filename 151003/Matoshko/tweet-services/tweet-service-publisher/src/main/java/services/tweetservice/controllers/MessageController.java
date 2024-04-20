package services.tweetservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
import services.tweetservice.domain.request.MessageRequestTo;
import services.tweetservice.domain.response.MessageResponseTo;
import services.tweetservice.exceptions.NoSuchMessageException;
import services.tweetservice.exceptions.ServiceException;
import services.tweetservice.serivces.MessageServicePublisher;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageServicePublisher messageServicePublisher;

    @Autowired
    public MessageController(MessageServicePublisher messageServicePublisher) {
        this.messageServicePublisher = messageServicePublisher;
    }

    @PostMapping
    public ResponseEntity<MessageResponseTo> createMessage(@RequestBody MessageRequestTo messageRequestTo) throws ServiceException {
        return ResponseEntity.status(HttpStatus.CREATED).body(messageServicePublisher.create(messageRequestTo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponseTo> findMessageById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(messageServicePublisher.findMessageById(id).orElseThrow(() -> new NoSuchMessageException(id)));
    }

    @GetMapping
    public ResponseEntity<List<MessageResponseTo>> findAllMessages() {
        List<MessageResponseTo> messageResponseToList = messageServicePublisher.read();
        return ResponseEntity.status(HttpStatus.OK).body(messageResponseToList);
    }

    @PutMapping
    public ResponseEntity<MessageResponseTo> updateMessage(@RequestBody MessageRequestTo messageRequestTo) throws ServiceException {
        return ResponseEntity.status(HttpStatus.OK).body(messageServicePublisher.update(messageRequestTo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteMessageById(@PathVariable Long id) throws ServiceException {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(messageServicePublisher.delete(id));
    }
}
