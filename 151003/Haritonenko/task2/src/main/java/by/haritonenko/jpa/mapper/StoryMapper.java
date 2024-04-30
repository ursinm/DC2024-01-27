package by.haritonenko.jpa.mapper;

import by.haritonenko.jpa.dto.request.CreateStoryDto;
import by.haritonenko.jpa.dto.request.UpdateStoryDto;
import by.haritonenko.jpa.dto.response.StoryResponseDto;
import by.haritonenko.jpa.model.Story;
import org.mapstruct.*;

@Mapper
public interface StoryMapper {

    @Mapping(target="authorId", source="author.id")
    StoryResponseDto toStoryResponse(Story story);

    @Mapping(target = "author.id", source = "authorId")
    Story toStory(CreateStoryDto storyRequest);

    @Mapping(target = "author.id", source = "authorId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Story toStory(UpdateStoryDto storyRequest, @MappingTarget Story story);
}
