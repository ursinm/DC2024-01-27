package com.example.restapplication.mappers;

import com.example.restapplication.dto.StoryRequestTo;
import com.example.restapplication.dto.StoryResponseTo;
import com.example.restapplication.entites.Story;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StoryMapper {
    Story toStory(StoryRequestTo story);
    @Mapping(target = "userId", source = "story.user.id")
    StoryResponseTo toStoryResponse(Story story);
}
