package by.bsuir.messageapp.service.mapper;

import by.bsuir.messageapp.model.entity.Message;
import by.bsuir.messageapp.model.request.MessageRequestTo;
import by.bsuir.messageapp.model.response.MessageResponseTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageResponseTo getResponse(Message message);
    List<MessageResponseTo> getListResponse(Iterable<Message> messages);
    Message getMessage(MessageRequestTo messageRequestTo);
}
