package app.mapper;

import app.entities.Tweet;
import app.dto.TweetRequestTo;
import app.dto.TweetResponseTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = TweetMapper.class)
public interface TweetListMapper {
    List<Tweet> toTweetList(List<TweetRequestTo> tweets);
    List<TweetResponseTo> toTweetResponseList(List<Tweet> tweets);
}
