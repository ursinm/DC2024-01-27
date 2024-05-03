package com.example.lab2.Mapper;

import com.example.lab2.DTO.StoryRequestTo;
import com.example.lab2.DTO.StoryResponseTo;
import com.example.lab2.Model.Story;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = StoryMapper.class)
public interface StoryListMapper {
    List<Story> toStoryList(List<StoryRequestTo> list);

    List<StoryResponseTo> toStoryResponseList(List<Story> list);
}
