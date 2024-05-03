package org.example.discussion.impl.post.service;

import lombok.RequiredArgsConstructor;
import org.example.discussion.api.exception.DuplicateEntityException;
import org.example.discussion.api.exception.EntityNotFoundException;
import org.example.discussion.impl.post.Post;
import org.example.discussion.impl.post.PostRepository;
import org.example.discussion.impl.post.dto.PostRequestTo;
import org.example.discussion.impl.post.dto.PostResponseTo;
import org.example.discussion.impl.post.mapper.Impl.PostMapperImpl;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final PostMapperImpl postMapper;
    private final String ENTITY_NAME = "post";

    private final String ISSUE_PATH = "http://localhost:24110/api/v1.0/issues/";


    public List<PostResponseTo> getPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostResponseTo> postsTo = new ArrayList<>();
        for (var item : posts) {
            postsTo.add(postMapper.postToResponseTo(item));
        }

        return postsTo;
    }

    public PostResponseTo getPostById(BigInteger id) throws EntityNotFoundException {
        Optional<Post> post = postRepository.findBy_id("local", id);
        if (post.isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
        return postMapper.postToResponseTo(post.get());
    }

    public PostResponseTo savePost(PostRequestTo postTO) throws EntityNotFoundException, DuplicateEntityException {

        Post post = postRepository.save(postMapper.dtoToEntity(postTO, "local"));
        return postMapper.postToResponseTo(post);

    }

    public PostResponseTo updatePost(PostRequestTo postTO) throws EntityNotFoundException, DuplicateEntityException {
        Post post = postRepository.save(postMapper.dtoToEntity(postTO, "local"));
        return postMapper.postToResponseTo(post);

    }

    public void deletePost(BigInteger id) throws EntityNotFoundException {
        if (postRepository.findBy_id("local", id).isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
        postRepository.deleteBy_id("local", id);
    }
}
