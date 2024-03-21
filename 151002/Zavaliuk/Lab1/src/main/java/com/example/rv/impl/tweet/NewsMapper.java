package com.example.rv.impl.tweet;

import java.util.List;

public interface NewsMapper {

    NewsRequestTo tweetToRequestTo(News news);

    List<NewsRequestTo> tweetToRequestTo(Iterable<News> tweets);

    News dtoToEntity(NewsRequestTo newsRequestTo);

    List<News> dtoToEntity(Iterable<NewsRequestTo> tweetRequestTos);

    NewsResponseTo tweetToResponseTo(News news);

    List<NewsResponseTo> tweetToResponseTo(Iterable<News> tweets);
}
