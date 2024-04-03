package app.mapper;

import app.dto.TweetRequestTo;
import app.dto.TweetResponseTo;
import app.entities.Tweet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TweetMapper {
    Tweet tweetRequestToTweet(TweetRequestTo tweetRequestTo);

    TweetResponseTo tweetToTweetResponse(Tweet tweet);
}
