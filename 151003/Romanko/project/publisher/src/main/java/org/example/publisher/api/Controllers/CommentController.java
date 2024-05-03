package org.example.publisher.api.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.publisher.api.exception.DuplicateEntityException;
import org.example.publisher.api.exception.EntityNotFoundException;
import org.example.publisher.impl.comment.dto.CommentAddedResponseTo;
import org.example.publisher.impl.comment.dto.CommentRequestTo;
import org.example.publisher.impl.comment.dto.CommentResponseTo;
import org.example.publisher.impl.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1.0/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponseTo> getComments() {
        return commentService.getComments();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseTo getCommentById(@PathVariable BigInteger id) throws EntityNotFoundException, InterruptedException {
        return commentService.getCommentById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentAddedResponseTo saveComment(@Valid @RequestBody CommentRequestTo commentTo) throws DuplicateEntityException, EntityNotFoundException, InterruptedException {
        return commentService.saveComment(commentTo);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseTo updateComment(@Valid @RequestBody CommentRequestTo commentTo) throws DuplicateEntityException, EntityNotFoundException, JsonProcessingException, InterruptedException {
        return commentService.updateComment(commentTo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable BigInteger id) throws EntityNotFoundException, InterruptedException {
        commentService.deleteComment(id);
    }
}
