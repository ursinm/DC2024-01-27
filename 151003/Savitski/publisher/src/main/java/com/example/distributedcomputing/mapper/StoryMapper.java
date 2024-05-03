package com.example.distributedcomputing.mapper;

import com.example.distributedcomputing.model.entity.Editor;
import com.example.distributedcomputing.model.entity.Story;
import com.example.distributedcomputing.model.request.StoryRequestTo;
import com.example.distributedcomputing.model.response.StoryResponseTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StoryMapper {
    Story dtoToEntity(StoryRequestTo storyRequestTo);
    List<Editor> dtoToEntity(Iterable<Story> stories);

    StoryResponseTo entityToDto(Story story);

    List<StoryResponseTo> entityToDto(Iterable<Story> stories);
}
