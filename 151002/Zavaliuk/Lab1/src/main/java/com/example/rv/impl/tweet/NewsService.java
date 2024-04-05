package com.example.rv.impl.tweet;

import com.example.rv.api.repository.CrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsService {
    public final CrudRepository<News, Long> tweetCrudRepository;
    public final NewsMapperImpl tweetMapper;
}
