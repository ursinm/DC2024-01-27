package com.example.rv.impl.tweet;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class NewsMapperImpl implements NewsMapper {
    @Override
    public NewsRequestTo tweetToRequestTo(News news) {
        return new NewsRequestTo(
                news.getId(),
                news.getEditorId(),
                news.getTitle(),
                news.getContent(),
                news.getCreated(),
                news.getModified()
        );
    }

    @Override
    public List<NewsRequestTo> tweetToRequestTo(Iterable<News> tweets) {
        return StreamSupport.stream(tweets.spliterator(), false)
                .map(this::tweetToRequestTo)
                .collect(Collectors.toList());
    }

    @Override
    public News dtoToEntity(NewsRequestTo newsRequestTo) {
        return new News(
                newsRequestTo.id(),
                newsRequestTo.editorId(),
                newsRequestTo.title(),
                newsRequestTo.content(),
                newsRequestTo.created(),
                newsRequestTo.modified());
    }

    @Override
    public List<News> dtoToEntity(Iterable<NewsRequestTo> tweetRequestTos) {
        return StreamSupport.stream(tweetRequestTos.spliterator(), false)
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public NewsResponseTo tweetToResponseTo(News news) {
        return new NewsResponseTo(
                news.getId(),
                news.getEditorId(),
                news.getTitle(),
                news.getContent(),
                news.getCreated(),
                news.getModified()
        );
    }

    @Override
    public List<NewsResponseTo> tweetToResponseTo(Iterable<News> tweets) {
        return StreamSupport.stream(tweets.spliterator(), false)
                .map(this::tweetToResponseTo)
                .collect(Collectors.toList());
    }
}
