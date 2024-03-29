package app.mapper;

import app.dto.MessageRequestTo;
import app.dto.MessageResponseTo;
import app.entities.Message;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = MessageMapper.class)
public interface MessageListMapper {
    List<Message> toMessageList(List<MessageRequestTo> messages);
    List<MessageResponseTo> toMessageResponseList(List<Message> messages);
}
