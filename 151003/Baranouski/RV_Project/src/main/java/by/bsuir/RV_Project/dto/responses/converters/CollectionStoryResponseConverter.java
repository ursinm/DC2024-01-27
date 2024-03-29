package by.bsuir.RV_Project.dto.responses.converters;

import by.bsuir.RV_Project.domain.Story;
import by.bsuir.RV_Project.dto.responses.StoryResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = StoryResponseConverter.class)
public interface CollectionStoryResponseConverter {
    List<StoryResponseDto> toListDto(List<Story> labels);
}
