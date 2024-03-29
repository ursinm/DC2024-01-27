package com.example.restapplication.mappers;

import com.example.restapplication.dto.StoryRequestTo;
import com.example.restapplication.dto.StoryResponseTo;
import com.example.restapplication.entites.Story;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StoryMapper {
    Story toStory(StoryRequestTo story);
    StoryResponseTo toStoryResponse(Story story);
}
