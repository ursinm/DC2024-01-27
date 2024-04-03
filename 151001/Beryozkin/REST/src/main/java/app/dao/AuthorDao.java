package app.dao;

import app.entities.Author;
import java.util.Optional;

public interface AuthorDao extends BaseDao<Author> {
    Optional<Author> getAuthorByTweetId(long tweetId);
}
