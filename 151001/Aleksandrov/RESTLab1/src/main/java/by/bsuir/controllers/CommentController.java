package by.bsuir.controllers;

import by.bsuir.dto.CommentRequestTo;
import by.bsuir.dto.CommentResponseTo;
import by.bsuir.dto.EditorResponseTo;
import by.bsuir.exceptions.comment.CommentDeleteException;
import by.bsuir.exceptions.comment.CommentUpdateException;
import by.bsuir.exceptions.editor.EditorDeleteException;
import by.bsuir.exceptions.editor.EditorUpdateException;
import by.bsuir.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/comments")
public class CommentController {
    @Autowired
    CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentResponseTo>> getComments() {
        return ResponseEntity.status(200).body(commentService.getComments());
    }
    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseTo> getComment(@PathVariable Long id) {
        return ResponseEntity.status(200).body(commentService.getCommentById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id){
        try {
            commentService.deleteComment(id);
        } catch (CommentDeleteException exception){
            return ResponseEntity.status(exception.getStatus()).build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CommentResponseTo> saveComment(@RequestBody CommentRequestTo comment){
        CommentResponseTo savedComment = commentService.saveComment(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
    }

    @PutMapping()
    public ResponseEntity<CommentResponseTo> updateComment(@RequestBody CommentRequestTo comment){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(comment));
        } catch (CommentUpdateException exception){
            return ResponseEntity.status(exception.getStatus()).build();
        }
    }
}
