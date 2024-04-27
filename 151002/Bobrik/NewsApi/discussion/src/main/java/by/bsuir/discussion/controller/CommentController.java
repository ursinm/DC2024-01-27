package by.bsuir.discussion.controller;

import by.bsuir.discussion.model.response.CommentResponseTo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import by.bsuir.discussion.model.request.CommentRequestTo;
import by.bsuir.discussion.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/v1.0/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentResponseTo>> getAll() {
        return ResponseEntity.ok(commentService.findAll());
    }

    @PostMapping
    public ResponseEntity<CommentResponseTo> create(@Valid @RequestBody CommentRequestTo dto,
                                                    HttpServletRequest request) {
        final Locale locale = request.getLocale();
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.create(dto, locale));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseTo> get(@Valid @PathVariable("id") Long id) {
        return ResponseEntity.ok(commentService.findById(id));
    }

    @PutMapping
    public ResponseEntity<CommentResponseTo> update(@Valid @RequestBody CommentRequestTo dto) {
        return ResponseEntity.ok(commentService.update(dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @PathVariable("id") Long id) {
        commentService.removeById(id);
    }
}