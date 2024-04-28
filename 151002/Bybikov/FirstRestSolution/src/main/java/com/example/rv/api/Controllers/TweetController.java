package com.example.rv.api.Controllers;

import com.example.rv.impl.tweet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/v1.0")
public class TweetController {

    private final TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @RequestMapping(value = "/tweets", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    List<TweetResponseTo> getTweets() {
        return tweetService.tweetMapper.tweetToResponseTo(tweetService.tweetCrudRepository.getAll());
    }

    @RequestMapping(value = "/tweets", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    TweetResponseTo makeTweet(@RequestBody TweetRequestTo tweetRequestTo) {

        var toBack = tweetService.tweetCrudRepository.save(
                tweetService.tweetMapper.dtoToEntity(tweetRequestTo)
        );

        Tweet tweet = toBack.orElse(null);

        assert tweet != null;
        return tweetService.tweetMapper.tweetToResponseTo(tweet);
    }

    @RequestMapping(value = "/tweets/{id}", method = RequestMethod.GET)
    TweetResponseTo getTweet(@PathVariable Long id) {
        return tweetService.tweetMapper.tweetToResponseTo(
                Objects.requireNonNull(tweetService.tweetCrudRepository.getById(id).orElse(null)));
    }

    @RequestMapping(value = "/tweets", method = RequestMethod.PUT)
    TweetResponseTo updateTweet(@RequestBody TweetRequestTo tweetRequestTo, HttpServletResponse response) {
        Tweet tweet = tweetService.tweetMapper.dtoToEntity(tweetRequestTo);
        var newTweet = tweetService.tweetCrudRepository.update(tweet).orElse(null);
        if (newTweet != null) {
            response.setStatus(200);
            return tweetService.tweetMapper.tweetToResponseTo(newTweet);
        } else{
            response.setStatus(403);
            return tweetService.tweetMapper.tweetToResponseTo(tweet);
        }
    }

    @RequestMapping(value = "/tweets/{id}", method = RequestMethod.DELETE)
    int deleteTweet(@PathVariable Long id, HttpServletResponse response) {
        Tweet tweetToDelete = tweetService.tweetCrudRepository.getById(id).orElse(null);
        if (Objects.isNull(tweetToDelete)) {
            response.setStatus(403);
        } else {
            tweetService.tweetCrudRepository.delete(tweetToDelete);
            response.setStatus(204);
        }
        return 0;
    }
}
