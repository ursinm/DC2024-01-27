package app.mapper;

import app.dto.MessageRequestTo;
import app.dto.MessageResponseTo;
import app.entities.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    Message messageRequestToMessage(MessageRequestTo messageRequestTo);

    @Mapping(target = "tweetId", source = "message.tweet.id")
    MessageResponseTo messageToMessageResponse(Message message);
}
