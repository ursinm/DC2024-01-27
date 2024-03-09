package by.bsuir.RV_Project.dto.responses.converters;

import by.bsuir.RV_Project.domain.Story;
import by.bsuir.RV_Project.dto.responses.StoryResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StoryResponseConverter {
    @Mapping(source = "author.id", target = "authorId")
    StoryResponseDto toDto(Story story);
}
