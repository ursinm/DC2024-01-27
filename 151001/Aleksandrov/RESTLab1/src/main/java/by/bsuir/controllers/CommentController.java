package by.bsuir.controllers;

import by.bsuir.dto.CommentRequestTo;
import by.bsuir.dto.CommentResponseTo;
import by.bsuir.dto.EditorResponseTo;
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
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CommentResponseTo> saveComment(@RequestBody CommentRequestTo comment) {
        CommentResponseTo savedComment = commentService.saveComment(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
    }

    @PutMapping()
    public ResponseEntity<CommentResponseTo> updateComment(@RequestBody CommentRequestTo comment) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(comment));
    }

    @GetMapping("/byIssue/{id}")
    public ResponseEntity<CommentResponseTo> getEditorByIssueId(@PathVariable Long id){
        return ResponseEntity.status(200).body(commentService.getCommentByIssueId(id));
    }
}
