package org.example.publisher.api.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.publisher.api.exception.DuplicateEntityException;
import org.example.publisher.api.exception.EntityNotFoundException;
import org.example.publisher.impl.post.dto.PostAddedResponseTo;
import org.example.publisher.impl.post.dto.PostRequestTo;
import org.example.publisher.impl.post.dto.PostResponseTo;
import org.example.publisher.impl.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1.0/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponseTo> getPosts() {
        return postService.getPosts();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponseTo getPostById(@PathVariable BigInteger id) throws EntityNotFoundException, InterruptedException {
        return postService.getPostById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostAddedResponseTo savePost(@Valid @RequestBody PostRequestTo postTo) throws DuplicateEntityException, EntityNotFoundException, InterruptedException {
        return postService.savePost(postTo);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public PostResponseTo updatePost(@Valid @RequestBody PostRequestTo postTo) throws DuplicateEntityException, EntityNotFoundException, JsonProcessingException, InterruptedException {
        return postService.updatePost(postTo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable BigInteger id) throws EntityNotFoundException, InterruptedException {
        postService.deletePost(id);
    }
}
