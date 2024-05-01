package by.bsuir.mapper;

import by.bsuir.dto.MessageRequestTo;
import by.bsuir.dto.MessageResponseTo;
import by.bsuir.entities.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    Message messageRequestToMessage(MessageRequestTo messageRequestTo);

    @Mapping(target = "issueId", source = "message.issue.id")
    MessageResponseTo messageToMessageResponse(Message message);
}
