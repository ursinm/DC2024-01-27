package com.example.rv.api.Controllers;

import com.example.rv.impl.tweet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/v1.0")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @RequestMapping(value = "/news", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    List<NewsResponseTo> getTweets() {
        return newsService.tweetMapper.tweetToResponseTo(newsService.tweetCrudRepository.getAll());
    }

    @RequestMapping(value = "/news", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    NewsResponseTo makeTweet(@RequestBody NewsRequestTo newsRequestTo) {

        var toBack = newsService.tweetCrudRepository.save(
                newsService.tweetMapper.dtoToEntity(newsRequestTo)
        );

        News news = toBack.orElse(null);

        assert news != null;
        return newsService.tweetMapper.tweetToResponseTo(news);
    }

    @RequestMapping(value = "/news/{id}", method = RequestMethod.GET)
    NewsResponseTo getTweet(@PathVariable Long id) {
        return newsService.tweetMapper.tweetToResponseTo(
                Objects.requireNonNull(newsService.tweetCrudRepository.getById(id).orElse(null)));
    }

    @RequestMapping(value = "/news", method = RequestMethod.PUT)
    NewsResponseTo updateTweet(@RequestBody NewsRequestTo newsRequestTo, HttpServletResponse response) {
        News news = newsService.tweetMapper.dtoToEntity(newsRequestTo);
        var newTweet = newsService.tweetCrudRepository.update(news).orElse(null);
        if (newTweet != null) {
            response.setStatus(200);
            return newsService.tweetMapper.tweetToResponseTo(newTweet);
        } else{
            response.setStatus(403);
            return newsService.tweetMapper.tweetToResponseTo(news);
        }
    }

    @RequestMapping(value = "/news/{id}", method = RequestMethod.DELETE)
    int deleteTweet(@PathVariable Long id, HttpServletResponse response) {
        News newsToDelete = newsService.tweetCrudRepository.getById(id).orElse(null);
        if (Objects.isNull(newsToDelete)) {
            response.setStatus(403);
        } else {
            newsService.tweetCrudRepository.delete(newsToDelete);
            response.setStatus(204);
        }
        return 0;
    }
}
