package by.bsuir.rv.controller;

import by.bsuir.rv.dto.CommentRequestTo;
import by.bsuir.rv.dto.CommentResponseTo;
import by.bsuir.rv.exception.DuplicateEntityException;
import by.bsuir.rv.exception.EntititesNotFoundException;
import by.bsuir.rv.exception.EntityNotFoundException;
import by.bsuir.rv.service.comment.ICommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

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
    public CommentResponseTo getCommentById(@PathVariable BigInteger id) throws EntityNotFoundException {
        return commentService.getCommentById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseTo addComment(@RequestBody @Valid CommentRequestTo comment) throws DuplicateEntityException, EntityNotFoundException {
        return commentService.addComment(comment);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseTo updateComment(@RequestBody @Valid CommentRequestTo comment) throws EntityNotFoundException, DuplicateEntityException {
        return commentService.updateComment(comment);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable BigInteger id) throws EntityNotFoundException {
        commentService.deleteComment(id);
    }
}
