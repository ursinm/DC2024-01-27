package by.bsuir.springapi.controller;

import by.bsuir.springapi.model.request.CreatorRequestTo;
import by.bsuir.springapi.model.request.PostRequestTo;
import by.bsuir.springapi.model.response.CreatorResponseTo;
import by.bsuir.springapi.model.response.PostResponseTo;
import by.bsuir.springapi.service.PostService;
import by.bsuir.springapi.service.RestService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/posts")
@Data
@RequiredArgsConstructor
public class PostController {

    private final RestService<PostRequestTo, PostResponseTo> postService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponseTo> findAll() {
        return postService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponseTo create(@Valid @RequestBody PostRequestTo dto) {
        return postService.create(dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponseTo get(@Valid @PathVariable("id") Long id) {
        return postService.findById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public PostResponseTo update(@Valid @RequestBody PostRequestTo dto) {
        return postService.update(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @PathVariable("id") Long id) {
        postService.removeById(id);
    }
}
