package by.bsuir.mapper;

import by.bsuir.dto.MessageRequestTo;
import by.bsuir.dto.MessageResponseTo;
import by.bsuir.entities.Message;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = MessageMapper.class)
public interface MessageListMapper {

    List<Message> toMessageList(List<MessageRequestTo> Messages);
    List<MessageResponseTo> toMessageResponseList(List<Message> messages);
}
