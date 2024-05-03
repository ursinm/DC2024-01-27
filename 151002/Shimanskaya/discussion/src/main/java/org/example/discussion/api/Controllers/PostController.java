package org.example.discussion.api.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.discussion.api.exception.DuplicateEntityException;
import org.example.discussion.api.exception.EntityNotFoundException;
import org.example.discussion.impl.post.dto.PostRequestTo;
import org.example.discussion.impl.post.dto.PostResponseTo;
import org.example.discussion.impl.post.service.PostService;
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
    public PostResponseTo getPostById(@PathVariable BigInteger id) throws EntityNotFoundException {
        return postService.getPostById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponseTo savePost(@Valid @RequestBody PostRequestTo postTo) throws DuplicateEntityException, EntityNotFoundException {
        return postService.savePost(postTo);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public PostResponseTo updatePost(@Valid @RequestBody PostRequestTo postTo) throws DuplicateEntityException, EntityNotFoundException {
        return postService.savePost(postTo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable BigInteger id) throws EntityNotFoundException {
        postService.deletePost(id);
    }
}
