package app.mapper;

import app.dto.TweetRequestTo;
import app.dto.TweetResponseTo;
import app.entities.Tweet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = TweetMapper.class)
public interface TweetListMapper {
    List<Tweet> totweetList(List<TweetRequestTo> tweets);

    List<TweetResponseTo> toTweetResponseList(List<Tweet> tweets);
}
