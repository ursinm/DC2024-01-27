package com.example.publisher.controller;
import com.example.publisher.model.request.MessageRequestTo;
import com.example.publisher.model.response.MessageResponseTo;
import com.example.publisher.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1.0/messages")
@Data
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseTo findById(@Valid @PathVariable("id") BigInteger id) throws ExecutionException, InterruptedException {

        return messageService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MessageResponseTo> findAll() {

        return messageService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseTo create(@Valid @RequestBody MessageRequestTo request) throws JsonProcessingException, InterruptedException {
        return messageService.create(request);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseTo update(@Valid @RequestBody MessageRequestTo request) throws JsonProcessingException, InterruptedException {
        return messageService.update(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean removeById(@Valid @PathVariable("id") BigInteger id) throws InterruptedException {

        return messageService.removeById(id);
    }
}

