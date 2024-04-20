package com.example.rv.impl.tweet;

import com.example.rv.api.MemRepository.MemoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TweetRepository extends MemoryRepository<Tweet> {
    @Override
    public Optional<Tweet> save(Tweet entity) {
        entity.id = ids.incrementAndGet();
        map.put(entity.getId(), entity);
        return Optional.of(entity);
    }

    @Override
    public Optional<Tweet> update(Tweet tweet) {
        Long id = tweet.getId();
        Tweet memRepTweet = map.get(id);

        //maybe some checks for un existing users, but how??
        if (memRepTweet != null && (
                tweet.getEditorId() != null &&
                        tweet.getTitle().length() > 1 &&
                        tweet.getTitle().length() < 65 &&
                        tweet.getContent().length() > 3 &&
                        tweet.getContent().length() < 2049
                ) ){

        memRepTweet = tweet;
        } else return Optional.empty();

        return Optional.of(memRepTweet);
    }

    @Override
    public boolean delete(Tweet tweet){
        return map.remove(tweet.getId(), tweet);
    }
}
