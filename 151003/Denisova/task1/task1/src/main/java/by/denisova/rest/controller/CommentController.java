package by.denisova.rest.controller;

import by.denisova.rest.dto.request.CreateCommentDto;
import by.denisova.rest.dto.request.UpdateCommentDto;
import by.denisova.rest.dto.response.CommentResponseDto;
import by.denisova.rest.facade.CommentFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/comments")
public class CommentController {

    private final CommentFacade commentFacade;

    @GetMapping("/{id}")
    public CommentResponseDto findCommentById(@PathVariable("id") Long id) {
        return commentFacade.findById(id);
    }

    @GetMapping
    public List<CommentResponseDto> findAll() {
        return commentFacade.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CommentResponseDto saveComment(@RequestBody @Valid CreateCommentDto commentRequest) {
        return commentFacade.save(commentRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable("id") Long id) {
        commentFacade.delete(id);
    }

    @PutMapping
    public CommentResponseDto updateComment(@RequestBody @Valid UpdateCommentDto commentRequest) {
        return commentFacade.update(commentRequest);
    }
}

