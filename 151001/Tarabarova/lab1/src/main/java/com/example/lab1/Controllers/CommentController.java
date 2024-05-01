package com.example.lab1.Controllers;

import com.example.lab1.DTO.CommentRequestTo;
import com.example.lab1.DTO.CommentResponseTo;
import com.example.lab1.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/")
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping(value = "comments")
    public ResponseEntity<?> create(@RequestBody CommentRequestTo commentRequestTo) {
        CommentResponseTo comment = commentService.create(commentRequestTo);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @GetMapping(value = "comments", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> read() {
        final List<CommentResponseTo> list = commentService.readAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "comments/{id}")
    public ResponseEntity<?> read(@PathVariable(name = "id") int id) {
        CommentResponseTo comment = commentService.read(id);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PutMapping(value = "comments")
    public ResponseEntity<?> update(@RequestBody CommentRequestTo commentRequestTo) {
        CommentResponseTo comment = commentService.update(commentRequestTo, commentRequestTo.getId());
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @DeleteMapping(value = "comments/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        boolean isDeleted = commentService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
