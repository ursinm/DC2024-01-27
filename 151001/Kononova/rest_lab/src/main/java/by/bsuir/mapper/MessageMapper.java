package by.bsuir.mapper;

import by.bsuir.dto.MessageRequestTo;
import by.bsuir.dto.MessageResponseTo;
import by.bsuir.entities.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    Message MessageRequestToMessage(MessageRequestTo messageRequestTo);
    MessageResponseTo MessageToMessageResponse(Message message);
}
