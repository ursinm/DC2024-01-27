package com.example.lab2.Mapper;

import com.example.lab2.DTO.StoryRequestTo;
import com.example.lab2.DTO.StoryResponseTo;
import com.example.lab2.Model.Story;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StoryMapper {
    Story storyRequestToStory(StoryRequestTo storyRequestTo);
    @Mapping(target = "userId", source = "story.user.id")
    StoryResponseTo storyToStoryResponse(Story story);
}
