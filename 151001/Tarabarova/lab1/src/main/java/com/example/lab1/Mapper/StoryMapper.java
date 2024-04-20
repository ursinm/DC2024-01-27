package com.example.lab1.Mapper;

import com.example.lab1.DTO.StoryRequestTo;
import com.example.lab1.DTO.StoryResponseTo;
import com.example.lab1.Model.Story;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StoryMapper {
    Story storyReguestToStory(StoryRequestTo storyRequestTo);

    StoryResponseTo storyToStoryResponse(Story story);
}
