package services.tweetservice.domain.mapper;

import org.mapstruct.Mapper;
import services.tweetservice.domain.entity.Message;
import services.tweetservice.domain.request.MessageRequestTo;
import services.tweetservice.domain.response.MessageResponseTo;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    Message toMessage(MessageRequestTo messageRequestTo);
    MessageResponseTo toMessageResponseTo(Message message);
    Message toMessage(MessageResponseTo messageResponseTo);
}
