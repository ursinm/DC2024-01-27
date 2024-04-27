package by.bsuir.vladislavmatsiushenko.mapper;

import by.bsuir.vladislavmatsiushenko.dto.MessageRequestTo;
import by.bsuir.vladislavmatsiushenko.dto.MessageResponseTo;
import by.bsuir.vladislavmatsiushenko.model.Message;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring", uses = MessageMapper.class)
public interface MessageListMapper {
    List<Message> toMessageList(List<MessageRequestTo> messageRequestToList);

    List<MessageResponseTo> toMessageResponseList(List<Message> messageList);
}
