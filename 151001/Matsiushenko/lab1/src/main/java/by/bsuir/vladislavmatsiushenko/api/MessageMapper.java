package by.bsuir.vladislavmatsiushenko.api;

import by.bsuir.vladislavmatsiushenko.impl.bean.Message;
import by.bsuir.vladislavmatsiushenko.impl.dto.MessageRequestTo;
import by.bsuir.vladislavmatsiushenko.impl.dto.MessageResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper( MessageMapper.class );
    MessageResponseTo MessageToMessageResponseTo(Message message);
    MessageRequestTo MessageToMessageRequestTo(Message message);
    Message MessageResponseToToMessage(MessageResponseTo messageResponseTo);
    Message MessageRequestToToMessage(MessageRequestTo messageRequestTo);
}
