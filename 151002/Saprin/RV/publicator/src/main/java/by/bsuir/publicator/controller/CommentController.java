package by.bsuir.publicator.controller;

import by.bsuir.publicator.dto.CommentAddResponseTo;
import by.bsuir.publicator.dto.CommentRequestTo;
import by.bsuir.publicator.dto.CommentResponseTo;
import by.bsuir.publicator.exception.DuplicateEntityException;
import by.bsuir.publicator.exception.EntititesNotFoundException;
import by.bsuir.publicator.exception.EntityNotFoundException;
import by.bsuir.publicator.service.comment.ICommentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/comments")

public class CommentController {

    private final ICommentService commentService;

    @Autowired
    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponseTo> getComments() throws EntititesNotFoundException {
        return commentService.getComments();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseTo getCommentById(@PathVariable BigInteger id) throws EntityNotFoundException, ExecutionException, InterruptedException {
         return commentService.getCommentById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CommentAddResponseTo addComment(@RequestBody @Valid CommentRequestTo comment) throws DuplicateEntityException, EntityNotFoundException, JsonProcessingException, InterruptedException {
        return commentService.addComment(comment);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseTo updateComment(@RequestBody @Valid CommentRequestTo comment) throws EntityNotFoundException, DuplicateEntityException, JsonProcessingException, InterruptedException {
        return commentService.updateComment(comment);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable BigInteger id) throws EntityNotFoundException, InterruptedException {
        commentService.deleteComment(id);
    }
}
