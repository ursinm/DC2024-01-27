package by.bsuir.RV_Project.dto.responses.converters;

import by.bsuir.RV_Project.domain.Message;
import by.bsuir.RV_Project.dto.responses.MessageResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageResponseConverter {
    @Mapping(source = "story.id", target = "storyId")
    MessageResponseDto toDto(Message message);
}
