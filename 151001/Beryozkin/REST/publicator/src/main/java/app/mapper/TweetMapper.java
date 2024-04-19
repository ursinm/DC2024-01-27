package app.mapper;

import app.entities.Tweet;
import app.dto.TweetRequestTo;
import app.dto.TweetResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TweetMapper {
    Tweet tweetRequestToTweet (TweetRequestTo tweetRequestTo);
    @Mapping(target = "authorId", source = "tweet.author.id")
    TweetResponseTo tweetToTweetResponse(Tweet tweet);
}
