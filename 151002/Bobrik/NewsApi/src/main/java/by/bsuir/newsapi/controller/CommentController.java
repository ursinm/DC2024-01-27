package by.bsuir.newsapi.controller;

import by.bsuir.newsapi.model.request.CommentRequestTo;
import by.bsuir.newsapi.model.request.EditorRequestTo;
import by.bsuir.newsapi.model.response.CommentResponseTo;
import by.bsuir.newsapi.model.response.EditorResponseTo;
import by.bsuir.newsapi.service.CommentService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/comments")
@Data
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponseTo> findAll() {
        return commentService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseTo create(@Valid @RequestBody CommentRequestTo dto) {
        return commentService.create(dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseTo get(@Valid @PathVariable("id") Long id) {
        return commentService.findById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseTo update(@Valid @RequestBody CommentRequestTo dto) {
        return commentService.update(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @PathVariable("id") Long id) {
        commentService.removeById(id);
    }
}
