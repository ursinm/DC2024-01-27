package com.example.rv.impl.tweet;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TweetMapperImpl implements TweetMapper {
    @Override
    public TweetRequestTo tweetToRequestTo(Tweet tweet) {
        return new TweetRequestTo(
                tweet.getId(),
                tweet.getEditorId(),
                tweet.getTitle(),
                tweet.getContent(),
                tweet.getCreated(),
                tweet.getModified()
        );
    }

    @Override
    public List<TweetRequestTo> tweetToRequestTo(Iterable<Tweet> tweets) {
        return StreamSupport.stream(tweets.spliterator(), false)
                .map(this::tweetToRequestTo)
                .collect(Collectors.toList());
    }

    @Override
    public Tweet dtoToEntity(TweetRequestTo tweetRequestTo) {
        return new Tweet(
                tweetRequestTo.id(),
                tweetRequestTo.editorId(),
                tweetRequestTo.title(),
                tweetRequestTo.content(),
                tweetRequestTo.created(),
                tweetRequestTo.modified());
    }

    @Override
    public List<Tweet> dtoToEntity(Iterable<TweetRequestTo> tweetRequestTos) {
        return StreamSupport.stream(tweetRequestTos.spliterator(), false)
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public TweetResponseTo tweetToResponseTo(Tweet tweet) {
        return new TweetResponseTo(
                tweet.getId(),
                tweet.getEditorId(),
                tweet.getTitle(),
                tweet.getContent(),
                tweet.getCreated(),
                tweet.getModified()
        );
    }

    @Override
    public List<TweetResponseTo> tweetToResponseTo(Iterable<Tweet> tweets) {
        return StreamSupport.stream(tweets.spliterator(), false)
                .map(this::tweetToResponseTo)
                .collect(Collectors.toList());
    }
}
