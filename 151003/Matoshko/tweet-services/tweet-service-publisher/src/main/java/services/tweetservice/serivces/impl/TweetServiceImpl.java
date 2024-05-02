package services.tweetservice.serivces.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import services.tweetservice.domain.entity.Creator;
import services.tweetservice.domain.entity.Tweet;
import services.tweetservice.domain.entity.ValidationMarker;
import services.tweetservice.domain.mapper.TweetListMapper;
import services.tweetservice.domain.mapper.TweetMapper;
import services.tweetservice.domain.request.TweetRequestTo;
import services.tweetservice.domain.response.TweetResponseTo;
import services.tweetservice.exceptions.NoSuchCreatorException;
import services.tweetservice.exceptions.NoSuchTweetException;
import services.tweetservice.repositories.TweetRepository;
import services.tweetservice.serivces.CreatorService;
import services.tweetservice.serivces.TweetService;

import java.util.List;

@Service
@Transactional
@Validated
public class TweetServiceImpl implements TweetService {
    private final CreatorService creatorService;
    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;
    private final TweetListMapper tweetListMapper;

    @Autowired
    public TweetServiceImpl(CreatorService creatorService, TweetRepository tweetRepository, TweetMapper tweetMapper, TweetListMapper tweetListMapper) {
        this.creatorService = creatorService;
        this.tweetRepository = tweetRepository;
        this.tweetMapper = tweetMapper;
        this.tweetListMapper = tweetListMapper;
    }

    @Override
    @Validated(ValidationMarker.OnCreate.class)
    public TweetResponseTo create(@Valid TweetRequestTo entity) {
        Creator creator = creatorService.findCreatorByIdExt(entity.creatorId()).orElseThrow(() -> new NoSuchCreatorException(entity.creatorId()));
        Tweet tweet = tweetMapper.toTweet(entity);
        tweet.setCreator(creator);
        return tweetMapper.toTweetResponseTo(tweetRepository.save(tweet));
    }

    @Override
    public List<TweetResponseTo> read() {
        return tweetListMapper.toTweetResponseToList(tweetRepository.findAll());
    }

    @Override
    @Validated(ValidationMarker.OnUpdate.class)
    public TweetResponseTo update(@Valid TweetRequestTo entity) {
        if (tweetRepository.existsById(entity.id())) {
            Tweet tweet = tweetMapper.toTweet(entity);
            Tweet tweetRef = tweetRepository.getReferenceById(tweet.getId());
            tweet.setCreator(tweetRef.getCreator());
            tweet.setTweetStickerList(tweetRef.getTweetStickerList());
            //  tweetResponseTo.stickerList() = tweet.getTweetStickerList().stream().map(element -> stickerMapper.toStickerResponseTo(element.getSticker())).collect(Collectors.toList());
            return tweetMapper.toTweetResponseTo(tweetRepository.save(tweet));
        } else {
            throw new NoSuchCreatorException(entity.id());
        }
    }

    @Override
    public void delete(Long id) {
        if (tweetRepository.existsById(id)) {
            tweetRepository.deleteById(id);
        } else {
            throw new NoSuchTweetException(id);
        }

    }

    @Override
    public TweetResponseTo findTweetById(Long id) {
        Tweet tweet = tweetRepository.findById(id).orElseThrow(() -> new NoSuchTweetException(id));
        return tweetMapper.toTweetResponseTo(tweet);
    }

    @Override
    public boolean existsById(Long id) {
        return tweetRepository.existsById(id);
    }
}
