package by.bsuir.lab2.service.mapper;

import by.bsuir.lab2.entity.Creator;
import by.bsuir.lab2.entity.Tweet;
import by.bsuir.lab2.repository.CreatorRepository;
import by.bsuir.lab2.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomMapper {
    private final TweetRepository tweetRepository;
    private final CreatorRepository creatorRepository;

    @Named("tweetIdToTweetRef")
    public Tweet tweetIdToTweetRef(Long tweetId) {
        return tweetRepository.getReferenceById(tweetId);
    }

    @Named("creatorIdToCreatorRef")
    public Creator creatorIdToCreatorRef(Long creatorId) {
        return creatorRepository.getReferenceById(creatorId);
    }
}
