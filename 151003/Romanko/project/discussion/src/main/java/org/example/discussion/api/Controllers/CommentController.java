package org.example.discussion.api.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.discussion.api.exception.DuplicateEntityException;
import org.example.discussion.api.exception.EntityNotFoundException;
import org.example.discussion.impl.comment.dto.CommentRequestTo;
import org.example.discussion.impl.comment.dto.CommentResponseTo;
import org.example.discussion.impl.comment.service.CommentService;
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
    public CommentResponseTo getCommentById(@PathVariable BigInteger id) throws EntityNotFoundException {
        return commentService.getCommentById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseTo saveComment(@Valid @RequestBody CommentRequestTo commentTo) throws DuplicateEntityException, EntityNotFoundException {
        return commentService.saveComment(commentTo);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseTo updateComment(@Valid @RequestBody CommentRequestTo commentTo) throws DuplicateEntityException, EntityNotFoundException {
        return commentService.saveComment(commentTo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable BigInteger id) throws EntityNotFoundException {
        commentService.deleteComment(id);
    }
}
