package app.dao.impl;

import app.dao.MarkerDao;
import app.dao.TweetDao;
import app.exceptions.DeleteException;
import app.exceptions.UpdateException;
import app.entities.Marker;
import app.entities.Tweet;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TweetDaoImpl implements TweetDao {

    private long counter = 0;
    private final Map<Long, Tweet> map = new HashMap<>();

    @Autowired
    MarkerDao markerDao;

    @Override
    public Tweet save(Tweet entity) {
        counter++;
        map.put(counter, entity);
        entity.setId(counter);
        return entity;
    }

    @Override
    public void delete(long id) throws DeleteException {
        if (map.remove(id) == null) {
            throw new DeleteException("The Tweet has not been deleted", 40003L);
        }
    }

    @Override
    public List<Tweet> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<Tweet> findById(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Tweet update(Tweet updatedTweet) throws UpdateException {
        Long id = updatedTweet.getId();

        if (map.containsKey(id)) {
            Tweet existingTweet = map.get(id);
            BeanUtils.copyProperties(updatedTweet, existingTweet);
            return existingTweet;
        } else {
            throw new UpdateException("Tweet update failed", 40002L);
        }
    }


    @Override
    public Optional<Tweet> getTweetByCriteria(String markerName, Long markerId, String title, String content) {
        for (Tweet Tweet : map.values()) {
            if (matchesCriteria(Tweet, markerName, markerId, title, content)) {
                return Optional.ofNullable(Tweet);
            }
        }
        return Optional.empty();
    }

    private boolean matchesCriteria(Tweet Tweet, String markerName, Long markerId, String title, String content) {
        if (markerName != null) {
            Marker marker = markerDao.getMarkerByTweetId(Tweet.getId()).orElse(null);
            if (marker == null || !marker.getName().equals(markerName)) {
                return false;
            }
        }
        if (markerId != null) {
            if (!Objects.equals(Tweet.getMarkerId(), markerId)) {
                return false;
            }
        }
        if (title != null) {
            if (!Tweet.getTitle().equals(title)) {
                return false;
            }
        }
        if (content != null) {
            return Tweet.getContent().equals(content);
        }
        return true;
    }
}
