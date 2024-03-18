package app.services;

import app.dao.TweetDao;
import app.dto.TweetRequestTo;
import app.dto.TweetResponseTo;
import app.entities.Tweet;
import app.exceptions.DeleteException;
import app.exceptions.NotFoundException;
import app.exceptions.UpdateException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import app.mapper.TweetListMapper;
import app.mapper.TweetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class TweetService {
    @Autowired
    TweetMapper tweetMapper;
    @Autowired
    TweetDao tweetDao;
    @Autowired
    TweetListMapper tweetListMapper;

    public TweetResponseTo getTweetById(@Min(0) Long id) throws NotFoundException {
        Optional<Tweet> tweet = tweetDao.findById(id);
        return tweet.map(value -> tweetMapper.tweetToTweetResponse(value)).orElseThrow(() -> new NotFoundException("tweet not found!", 40004L));
    }

    public List<TweetResponseTo> getTweets() {
        return tweetListMapper.totweetResponseList(tweetDao.findAll());
    }

    public TweetResponseTo saveTweet(@Valid TweetRequestTo tweet) {
        Tweet tweetToSave = tweetMapper.tweetRequestToTweet(tweet);
        return tweetMapper.tweetToTweetResponse(tweetDao.save(tweetToSave));
    }

    public void deleteTweet(@Min(0) Long id) throws DeleteException {
        tweetDao.delete(id);
    }

    public TweetResponseTo updateTweet(@Valid TweetRequestTo tweet) throws UpdateException {
        Tweet tweetToUpdate = tweetMapper.tweetRequestToTweet(tweet);
        return tweetMapper.tweetToTweetResponse(tweetDao.update(tweetToUpdate));
    }

    public TweetResponseTo getTweetByCriteria(String markerName, Long markerId, String title, String content) {
        return tweetMapper.tweetToTweetResponse(tweetDao.getTweetByCriteria(markerName, markerId, title, content).orElseThrow(() -> new NotFoundException("tweet not found!", 40005L)));
    }
}
