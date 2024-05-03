package org.education.service;

import org.education.bean.DTO.MarkerResponseTo;
import org.education.bean.DTO.TweetResponseTo;
import org.education.bean.Marker;
import org.education.bean.Tweet;
import org.education.exception.AlreadyExists;
import org.education.exception.IncorrectValuesException;
import org.education.exception.NoSuchTweet;
import org.education.repository.CreatorRepository;
import org.education.repository.TweetRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@CacheConfig(cacheNames = "tweetCache")
public class TweetService {

    final TweetRepository tweetRepository;
    final CreatorRepository creatorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TweetService(TweetRepository tweetRepository, CreatorRepository creatorRepository, ModelMapper modelMapper) {
        this.tweetRepository = tweetRepository;
        this.creatorRepository = creatorRepository;
        this.modelMapper = modelMapper;
    }


    @CacheEvict(cacheNames = "tweets", allEntries = true)
    public TweetResponseTo create(Tweet tweet, int ownerId){
        if(tweetRepository.existsTweetByTitle(tweet.getTitle())) throw new AlreadyExists("Tweet with this title already exists");
        if(!creatorRepository.existsById(ownerId)) throw new IncorrectValuesException("There is no creator with this id");
        tweet.setCreator(creatorRepository.getReferenceById(ownerId));
        tweet.setCreated(LocalDateTime.now());
        tweet.setModified(LocalDateTime.now());
        tweetRepository.save(tweet);

        return modelMapper.map(tweet, TweetResponseTo.class);
    }

    @Cacheable(cacheNames = "tweets")
    public List<TweetResponseTo> getAll(){
        List<TweetResponseTo> res = new ArrayList<>();
        for(Tweet tweet : tweetRepository.findAll()){
            res.add(modelMapper.map(tweet, TweetResponseTo.class));
        }
        return res;
    }

    public boolean existsWithId(int id){
        return tweetRepository.existsById(id);
    }

    @Cacheable(cacheNames = "tweets", key = "#id", unless = "#result == null")
    public TweetResponseTo getById(int id){

        return modelMapper.map(tweetRepository.getReferenceById(id), TweetResponseTo.class);
    }

    @CacheEvict(cacheNames = "tweets", allEntries = true)
    public TweetResponseTo update(Tweet tweet){
        if(!tweetRepository.existsById(tweet.getId())) throw new NoSuchTweet("There is no such tweet with this id");
        Tweet prevTweet = tweetRepository.getReferenceById(tweet.getId());
        tweet.setCreated(prevTweet.getCreated());
        tweet.setModified(LocalDateTime.now());
        tweetRepository.save(tweet);
        return modelMapper.map(tweet, TweetResponseTo.class);
    }

    @Caching(evict = { @CacheEvict(cacheNames = "tweets", key = "#id"),
            @CacheEvict(cacheNames = "tweets", allEntries = true) })
    public void delete(int id){
        if(!tweetRepository.existsById(id)) throw new NoSuchTweet("There is no such tweet with this id");
        tweetRepository.deleteById(id);
    }
}
