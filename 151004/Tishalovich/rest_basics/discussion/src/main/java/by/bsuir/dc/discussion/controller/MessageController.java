package by.bsuir.dc.discussion.controller;

import by.bsuir.dc.discussion.entity.MessageRequestTo;
import by.bsuir.dc.discussion.entity.MessageResponseTo;
import by.bsuir.dc.discussion.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/messages")
    public MessageResponseTo create(@RequestBody MessageRequestTo requestTo) {
        return messageService.create(requestTo);
    }

    @GetMapping("/messages")
    public Iterable<MessageResponseTo> getAll() {
        return messageService.getAll();
    }
}
