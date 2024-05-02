package com.example.lab2.Controller;

import com.example.lab2.DTO.CommentRequestTo;
import com.example.lab2.DTO.CommentResponseTo;
import com.example.lab2.Service.CommentService;
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
    public ResponseEntity<?> read(
            @RequestParam(required = false, defaultValue = "0") Integer pageInd,
            @RequestParam(required = false, defaultValue = "20") Integer numOfElem,
            @RequestParam(required = false, defaultValue = "id") String sortedBy,
            @RequestParam(required = false, defaultValue = "desc") String direction)
    {
        final List<CommentResponseTo> list = commentService.readAll(pageInd, numOfElem, sortedBy, direction);
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
