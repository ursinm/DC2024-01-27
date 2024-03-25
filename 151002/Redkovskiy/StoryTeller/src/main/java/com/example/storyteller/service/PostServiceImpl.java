package com.example.storyteller.service;

import com.example.storyteller.dto.requestDto.PostRequestTo;
import com.example.storyteller.dto.responseDto.PostResponseTo;
import com.example.storyteller.model.Post;
import com.example.storyteller.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    private final StoryService storyService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, StoryService storyService) {
        this.postRepository = postRepository;
        this.storyService = storyService;
    }

    @Override
    public PostResponseTo create(PostRequestTo dto) {
        Post post = new Post();
        requestDtoToPost(post, dto);
        postRepository.save(post);
        return postToResponseDto(post);
    }

    @Override
    public Iterable<PostResponseTo> findAllDtos() {
        return StreamSupport.stream(postRepository.findAll().spliterator(), false)
                .map(this::postToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostResponseTo findDtoById(Long id) {
        return postToResponseDto(findPostById(id));
    }

    @Override
    public PostResponseTo update(PostRequestTo dto) {
        Post post = findPostById(dto.getId());
        requestDtoToPost(post, dto);
        postRepository.save(post);
        return postToResponseDto(post);
    }

    @Override
    public void delete(Long id) {
        Post post = findPostById(id);
        postRepository.delete(post);
    }

    private Post findPostById(Long id){
        return postRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Post with id " + id + " can't be found"));
    }

    void requestDtoToPost(Post post, PostRequestTo dto){
        post.setContent(dto.getContent());
        post.setStory(storyService.findStoryById(dto.getStoryId()));
    }

    PostResponseTo postToResponseDto(Post post){
        PostResponseTo dto = new PostResponseTo();
        dto.setId(post.getId());
        dto.setContent(post.getContent());
        dto.setStoryId(post.getStory().getId());
        return dto;
    }

}
