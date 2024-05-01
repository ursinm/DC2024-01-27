package by.bsuir.vladislavmatsiushenko.mapper;

import by.bsuir.vladislavmatsiushenko.dto.MessageRequestTo;
import by.bsuir.vladislavmatsiushenko.dto.MessageResponseTo;
import by.bsuir.vladislavmatsiushenko.model.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    Message messageRequestToMessage(MessageRequestTo messageRequestTo);

    @Mapping(target = "issueId", source = "message.issue.id")
    MessageResponseTo messageToMessageResponse(Message message);
}
