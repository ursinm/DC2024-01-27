package by.bsuir.RV_Project.dto.requests.converters;

import by.bsuir.RV_Project.domain.Story;
import by.bsuir.RV_Project.dto.requests.StoryRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StoryRequestConverter {
    @Mapping(source = "authorId", target = "author.id")
    Story fromDto(StoryRequestDto story);
}
