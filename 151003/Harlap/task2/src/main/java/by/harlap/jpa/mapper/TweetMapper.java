package by.harlap.jpa.mapper;

import by.harlap.jpa.dto.request.CreateTweetDto;
import by.harlap.jpa.dto.request.UpdateTweetDto;
import by.harlap.jpa.dto.response.TweetResponseDto;
import by.harlap.jpa.model.Tweet;
import org.mapstruct.*;

@Mapper
public interface TweetMapper {

    @Mapping(target="authorId", source="author.id")
    TweetResponseDto toTweetResponse(Tweet tweet);

    @Mapping(target = "author.id", source = "authorId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Tweet toTweet(UpdateTweetDto tweetRequest, @MappingTarget Tweet tweet);

    @Mapping(target = "author.id", source = "authorId")
    Tweet toTweet(CreateTweetDto tweetRequest);
}
