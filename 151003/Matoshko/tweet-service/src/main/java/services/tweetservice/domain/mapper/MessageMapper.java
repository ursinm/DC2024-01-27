package services.tweetservice.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import services.tweetservice.domain.entity.Message;
import services.tweetservice.domain.request.MessageRequestTo;
import services.tweetservice.domain.response.MessageResponseTo;

@Mapper(componentModel = "spring", uses = TweetMapper.class)
public interface MessageMapper {
    Message toMessage(MessageRequestTo messageRequestTo);
    @Mapping(source = "tweet.id", target = "tweetId")
    MessageResponseTo toMessageResponseTo(Message message);
}
