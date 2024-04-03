package app.dao;

import app.entities.Message;
import java.util.Optional;

public interface MessageDao extends BaseDao<Message> {
    Optional<Message> getMessageByTweetId (long tweetId);
}
