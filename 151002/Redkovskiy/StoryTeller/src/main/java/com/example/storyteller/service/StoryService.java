package com.example.storyteller.service;

import com.example.storyteller.dto.requestDto.StoryRequestTo;
import com.example.storyteller.dto.responseDto.StoryResponseTo;
import com.example.storyteller.model.Story;

public interface StoryService {

    StoryResponseTo create(StoryRequestTo dto);

    Iterable<StoryResponseTo> findAllDtos();

    StoryResponseTo findDtoById(Long id);

    Story findStoryById(Long id);

    StoryResponseTo update(StoryRequestTo dto);

    void delete(Long id);
}
