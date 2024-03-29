package by.bsuir.controllers;

import by.bsuir.dto.CommentRequestTo;
import by.bsuir.dto.CommentResponseTo;
import by.bsuir.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/comments")
public class CommentController {
    @Autowired
    CommentService commentService;

    @GetMapping
    public List<CommentResponseTo> getComments() {
        return commentService.getComments();
    }

    @GetMapping("/{id}")
    public CommentResponseTo getComment(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }

    @PostMapping
    public CommentResponseTo saveComment(@RequestHeader("Accept-Language") String acceptLanguageHeader, @RequestBody CommentRequestTo comment) {
        return commentService.saveComment(comment, acceptLanguageHeader);
    }

    @PutMapping()
    public CommentResponseTo updateComment(@RequestHeader("Accept-Language") String acceptLanguageHeader, @RequestBody CommentRequestTo comment) {
        return commentService.updateComment(comment, acceptLanguageHeader);
    }

    @GetMapping("/byIssue/{id}")
    public List<CommentResponseTo> getEditorByIssueId(@PathVariable Long id) {
        return commentService.getCommentByIssueId(id);
    }
}
