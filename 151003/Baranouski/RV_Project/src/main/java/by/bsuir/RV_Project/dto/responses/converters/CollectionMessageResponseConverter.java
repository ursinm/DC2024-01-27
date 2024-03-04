package by.bsuir.RV_Project.dto.responses.converters;

import by.bsuir.RV_Project.domain.Message;
import by.bsuir.RV_Project.dto.responses.MessageResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = MessageResponseConverter.class)
public interface CollectionMessageResponseConverter {
    List<MessageResponseDto> toListDto(List<Message> messages);
}
