package app.mapper;

import app.dto.MessageRequestTo;
import app.dto.MessageResponseTo;
import app.entities.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    Message messageRequestToMessage(MessageRequestTo messageRequestTo);

    MessageResponseTo messageToMessageResponse(Message message);
}
