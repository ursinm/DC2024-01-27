package app.controllers;

import app.dto.MessageRequestTo;
import app.dto.MessageResponseTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import app.services.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/messages")
public class MessageController {
    @Autowired
    MessageService messageService;

    @GetMapping
    public ResponseEntity<List<MessageResponseTo>> getMessages() {
        return ResponseEntity.status(200).body(messageService.getmessages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponseTo> getMessage(@PathVariable Long id) {
        return ResponseEntity.status(200).body(messageService.getMessageById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<MessageResponseTo> saveMessage(@RequestBody MessageRequestTo message) {
        MessageResponseTo savedmessage = messageService.savemessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedmessage);
    }

    @PutMapping()
    public ResponseEntity<MessageResponseTo> updateMessage(@RequestBody MessageRequestTo message) {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.updateMessage(message));
    }

    @GetMapping("/byTweet/{id}")
    public ResponseEntity<MessageResponseTo> getMessageByTweetId(@PathVariable Long id){
        return ResponseEntity.status(200).body(messageService.getMessageByTweetId(id));
    }
}
