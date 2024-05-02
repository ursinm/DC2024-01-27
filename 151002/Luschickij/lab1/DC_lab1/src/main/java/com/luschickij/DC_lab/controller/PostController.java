package com.luschickij.DC_lab.controller;

import com.luschickij.DC_lab.model.request.PostRequestTo;
import com.luschickij.DC_lab.model.response.PostResponseTo;
import com.luschickij.DC_lab.service.PostService;
import com.luschickij.DC_lab.model.request.PostRequestTo;
import com.luschickij.DC_lab.model.response.PostResponseTo;
import com.luschickij.DC_lab.service.PostService;
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
    private final PostService postService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponseTo findById(@Valid @PathVariable("id") Long id) {
        return postService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponseTo> findAll() {
        return postService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponseTo create(@Valid @RequestBody PostRequestTo request) {
        return postService.create(request);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public PostResponseTo update(@Valid @RequestBody PostRequestTo request) {
        return postService.update(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeById(@Valid @PathVariable("id") Long id) {
        postService.removeById(id);
    }
}
