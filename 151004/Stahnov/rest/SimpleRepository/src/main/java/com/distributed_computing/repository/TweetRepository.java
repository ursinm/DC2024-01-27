package com.distributed_computing.repository;

import com.distributed_computing.bean.Tweet;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TweetRepository {

    Map<Integer, Tweet> tweets = new HashMap<>();

    public List<Tweet> getAll(){
        List<Tweet> res = new ArrayList<>();

        for(Map.Entry<Integer, Tweet> entry : tweets.entrySet()){
            res.add(entry.getValue());
        }

        return res;
    }

    public Optional<Tweet> save(Tweet tweet){
        Tweet prevTweet = tweets.getOrDefault(tweet.getId(), null);
        tweets.put(tweet.getId(), tweet);
        return Optional.ofNullable(prevTweet);
    }


    public Optional<Tweet> getById(int id){
        return Optional.ofNullable(tweets.getOrDefault(id, null));
    }

    public Tweet delete(int id){
        return tweets.remove(id);
    }
}
