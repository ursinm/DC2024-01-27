package service.tweetservicediscussion.domain.mapper;

import org.mapstruct.Mapper;
import service.tweetservicediscussion.domain.entity.Message;
import service.tweetservicediscussion.domain.request.MessageRequestTo;
import service.tweetservicediscussion.domain.response.MessageResponseTo;

import java.util.List;

@Mapper(componentModel = "spring", uses = MessageMapper.class)
public interface MessageListMapper {
    List<Message> toMessageList(List<MessageRequestTo> messageRequestToList);

    List<MessageResponseTo> toMessageResponseToList(List<Message> messageList);
}
