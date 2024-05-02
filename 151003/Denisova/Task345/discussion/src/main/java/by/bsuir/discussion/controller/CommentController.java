package by.bsuir.discussion.controller;

import by.bsuir.discussion.model.request.CommentRequestTo;
import by.bsuir.discussion.model.response.CommentResponseTo;
import by.bsuir.discussion.service.CommentService;
import by.bsuir.discussion.service.kafka.KafkaConsumerService;
import by.bsuir.discussion.service.kafka.KafkaProducerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final KafkaProducerService producerService;
    private final KafkaConsumerService consumerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponseTo> findAll() {

        producerService.sendMessage("DECLINE");

        return commentService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseTo create(@Valid @RequestBody CommentRequestTo dto) {

        producerService.sendMessage("APPROVE");

        return commentService.create(dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseTo get(@Valid @PathVariable("id") Long id) {

       producerService.sendMessage("PENDING");

        return commentService.findById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseTo update(@Valid @RequestBody CommentRequestTo dto) {

        producerService.sendMessage("APPROVE");

        return commentService.update(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @PathVariable("id") Long id) {

        producerService.sendMessage("DECLINE");

        commentService.removeById(id);
    }
}