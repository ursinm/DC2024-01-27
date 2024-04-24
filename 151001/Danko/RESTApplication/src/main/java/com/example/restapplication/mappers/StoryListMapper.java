package com.example.restapplication.mappers;

import com.example.restapplication.dto.StoryRequestTo;
import com.example.restapplication.dto.StoryResponseTo;
import com.example.restapplication.entites.Story;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = StoryMapper.class)
public interface StoryListMapper {
    List<Story> toStoryList(List<StoryRequestTo> story);
    List<StoryResponseTo> toStoryResponseList(List<Story> story);
}
