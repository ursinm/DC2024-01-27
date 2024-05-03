package by.haritonenko.rest.mapper;

import by.haritonenko.rest.dto.request.CreateStoryDto;
import by.haritonenko.rest.dto.request.UpdateStoryDto;
import by.haritonenko.rest.dto.response.StoryResponseDto;
import by.haritonenko.rest.model.Story;
import org.mapstruct.*;

@Mapper
public interface StoryMapper {

    StoryResponseDto toStoryResponse(Story story);

    @Mapping(target = "created", expression = "java(java.time.LocalDateTime.now())")
    Story toStory(CreateStoryDto storyRequest);

    @Mapping(target = "modified", expression = "java(java.time.LocalDateTime.now())")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Story toStory(UpdateStoryDto storyRequest, @MappingTarget Story story);
}
