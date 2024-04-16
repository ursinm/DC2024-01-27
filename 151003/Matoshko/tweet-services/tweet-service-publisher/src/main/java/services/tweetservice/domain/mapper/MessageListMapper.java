package services.tweetservice.domain.mapper;

import org.mapstruct.Mapper;
import services.tweetservice.domain.entity.Message;
import services.tweetservice.domain.request.MessageRequestTo;
import services.tweetservice.domain.response.MessageResponseTo;

import java.util.List;

@Mapper(componentModel = "spring", uses = MessageMapper.class)
public interface MessageListMapper {
    List<Message> toMessageList(List<MessageRequestTo> messageRequestToList);

    List<MessageResponseTo> toMessageResponseToList(List<Message> messageList);
}
