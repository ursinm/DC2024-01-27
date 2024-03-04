package services.tweetservice.domain.mapper;

import org.mapstruct.Mapper;
import services.tweetservice.domain.entity.Creator;
import services.tweetservice.domain.request.CreatorRequestTo;
import services.tweetservice.domain.response.CreatorResponseTo;

@Mapper(componentModel = "spring", uses = TweetListMapper.class)
public interface CreatorMapper {
    Creator toCreator(CreatorRequestTo creatorRequestTo);

    CreatorResponseTo toCreatorResponseTo(Creator creator);
}
