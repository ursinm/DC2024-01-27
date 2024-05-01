package com.example.rv.impl.tweet;

import com.example.rv.api.repository.CrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TweetService {
    public final CrudRepository<Tweet, Long> tweetCrudRepository;
    public final TweetMapperImpl tweetMapper;
}
