package com.distributed_computing.rest.service;

import com.distributed_computing.repository.TweetRepository;
import com.distributed_computing.bean.Tweet;
import com.distributed_computing.rest.exception.NoSuchTweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TweetService {

    private static int ind = 0;

    @Autowired
    TweetRepository tweetRepository;

    public Tweet create(Tweet tweet){
        tweet.setId(ind);
        ind++;

        tweet.setCreated(LocalDateTime.now());
        tweet.setModified(LocalDateTime.now());
        tweetRepository.save(tweet);

        return tweet;
    }

    public List<Tweet> getAll(){
        List<Tweet> response = new ArrayList<>();

//        for(Creator creator : creatorRepository.getAll()){
//            response.add(modelMapper.map(creator, CreatorResponseTo.class));
//        }


        return tweetRepository.getAll();
    }

    public Optional<Tweet> getById(int id){
        return tweetRepository.getById(id);
    }

    public Tweet update(Tweet tweet){
        if(tweetRepository.getById(tweet.getId()).isEmpty()) throw new NoSuchTweet("There is no such tweet with this id");
        Tweet prevTweet = tweetRepository.getById(tweet.getId()).get();
        tweet.setCreated(prevTweet.getCreated());
        tweet.setModified(LocalDateTime.now());
        tweetRepository.save(tweet);
        return tweet;
    }

    public void delete(int id){
        if(tweetRepository.delete(id) == null) throw new NoSuchTweet("There is no such tweet with this id");
    }
}
