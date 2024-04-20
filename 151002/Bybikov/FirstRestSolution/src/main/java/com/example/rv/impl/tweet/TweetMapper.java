package com.example.rv.impl.tweet;

import java.util.List;

public interface TweetMapper {

    TweetRequestTo tweetToRequestTo(Tweet tweet);

    List<TweetRequestTo> tweetToRequestTo(Iterable<Tweet> tweets);

    Tweet dtoToEntity(TweetRequestTo tweetRequestTo);

    List<Tweet> dtoToEntity(Iterable<TweetRequestTo> tweetRequestTos);

    TweetResponseTo tweetToResponseTo(Tweet tweet);

    List<TweetResponseTo> tweetToResponseTo(Iterable<Tweet> tweets);
}
