package service.tweetservicediscussion.domain.mapper;

import org.mapstruct.Mapper;
import service.tweetservicediscussion.domain.entity.Message;
import service.tweetservicediscussion.domain.request.MessageRequestTo;
import service.tweetservicediscussion.domain.response.MessageResponseTo;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    Message toMessage(MessageRequestTo messageRequestTo);
    MessageResponseTo toMessageResponseTo(Message message);
}
