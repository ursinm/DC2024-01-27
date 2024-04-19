package app.controllers;

import app.dto.MessageRequestTo;
import app.dto.MessageResponseTo;
import app.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/messages")
public class MessageController {
    @Autowired
    MessageService messageService;

    @GetMapping
    public List<MessageResponseTo> getMessages() {
        return messageService.getMessages();
    }

    @GetMapping("/{id}")
    public MessageResponseTo getMessage(@PathVariable Long id) {
        return messageService.getMessageById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
    }

    @PostMapping
    public MessageResponseTo saveMessage(@RequestHeader("Accept-Language") String acceptLanguageHeader, @RequestBody MessageRequestTo message) {
        return messageService.saveMessage(message, acceptLanguageHeader);
    }

    @PutMapping()
    public MessageResponseTo updateMessage(@RequestHeader("Accept-Language") String acceptLanguageHeader, @RequestBody MessageRequestTo message) {
        return messageService.updateMessage(message, acceptLanguageHeader);
    }

    @GetMapping("/byTweet/{id}")
    public List<MessageResponseTo> getAuthorByTweetId(@PathVariable Long id) {
        return messageService.getMessageByTweetId(id);
    }
}
