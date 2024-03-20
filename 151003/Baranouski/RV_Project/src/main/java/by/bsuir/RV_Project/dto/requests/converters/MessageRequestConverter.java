package by.bsuir.RV_Project.dto.requests.converters;

import by.bsuir.RV_Project.domain.Message;
import by.bsuir.RV_Project.dto.requests.MessageRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageRequestConverter {
    @Mapping(source = "storyId", target = "story.id")
    Message fromDto(MessageRequestDto message);
}
