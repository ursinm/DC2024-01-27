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
    CommentService CommentService;

    @GetMapping
    public List<CommentResponseTo> getComments() {
        return CommentService.getComments();
    }
    @GetMapping("/{id}")
    public CommentResponseTo getComment(@PathVariable Long id) {
        return CommentService.getCommentById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id){CommentService.deleteComment(id);}

    @PostMapping
    public CommentResponseTo saveComment(@RequestBody CommentRequestTo comment){
        return CommentService.saveComment(comment);
    }

    @PostMapping("/{id}")
    public CommentResponseTo updateComment(@RequestBody CommentRequestTo comment, @PathVariable Long id){
        return CommentService.updateComment(comment, id);
    }
}
