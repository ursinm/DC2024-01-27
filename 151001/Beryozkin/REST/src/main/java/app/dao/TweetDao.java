package app.dao;

import app.entities.Tweet;
import java.util.Optional;

public interface TweetDao extends BaseDao<Tweet> {
    Optional<Tweet> getTweetByCriteria(String markerName, Long markerId, String title, String content);
}
