package services.tweetservice.domain.mapper;

import org.mapstruct.Mapper;
import services.tweetservice.domain.entity.Tweet;
import services.tweetservice.domain.request.TweetRequestTo;
import services.tweetservice.domain.response.TweetResponseTo;

import java.util.List;

@Mapper(componentModel = "spring", uses = TweetMapper.class)
public interface TweetListMapper {
    List<Tweet> toTweetList(List<TweetRequestTo> tweetRequestToList);

    List<TweetResponseTo> toTweetResponseToList(List<Tweet> tweetList);
}
