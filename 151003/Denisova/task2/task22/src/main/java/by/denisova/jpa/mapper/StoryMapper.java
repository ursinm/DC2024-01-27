package by.denisova.jpa.mapper;

import by.denisova.jpa.dto.request.CreateStoryDto;
import by.denisova.jpa.dto.request.UpdateStoryDto;
import by.denisova.jpa.dto.response.StoryResponseDto;
import by.denisova.jpa.model.Story;
import org.mapstruct.*;

@Mapper
public interface StoryMapper {

    @Mapping(target="editorId", source="editor.id")
    StoryResponseDto toStoryResponse(Story story);

    @Mapping(target = "editor.id", source = "editorId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Story toStory(UpdateStoryDto storyRequest, @MappingTarget Story story);

    @Mapping(target = "editor.id", source = "editorId")
    Story toStory(CreateStoryDto storyRequest);
}
