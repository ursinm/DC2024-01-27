package com.example.storyteller.service;

import com.example.storyteller.dto.requestDto.PostRequestTo;
import com.example.storyteller.dto.responseDto.PostResponseTo;

public interface PostService {

    PostResponseTo create(PostRequestTo dto);

    Iterable<PostResponseTo> findAllDtos();

    PostResponseTo findDtoById(Long id);

    PostResponseTo update(PostRequestTo dto);

    void delete(Long id);
    
}
