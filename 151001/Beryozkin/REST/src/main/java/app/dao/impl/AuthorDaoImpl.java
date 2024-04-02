package app.dao.impl;

import app.exceptions.DeleteException;
import app.exceptions.NotFoundException;
import app.dao.AuthorDao;
import app.dao.TweetDao;
import app.entities.Author;
import app.entities.Tweet;
import app.exceptions.UpdateException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AuthorDaoImpl implements AuthorDao {

    private long counter = 0;
    private final Map<Long, Author> map = new HashMap<>();
    @Autowired
    private TweetDao tweetDao;

    @Override
    public Author save(Author entity) {
        counter++;
        map.put(counter, entity);
        entity.setId(counter);
        return entity;
    }

    @Override
    public void delete(long id) throws DeleteException {
        if (map.remove(id) == null) {
            throw new DeleteException("The Author has not been deleted", 40003L);
        }
    }

    @Override
    public List<Author> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Author update(Author updatedAuthor) throws UpdateException {
        Long id = updatedAuthor.getId();

        if (map.containsKey(id)) {
            Author existingAuthor = map.get(id);
            BeanUtils.copyProperties(updatedAuthor, existingAuthor);
            return existingAuthor;
        } else {
            throw new UpdateException("Author update failed", 40002L);
        }
    }

    @Override
    public Optional<Author> getAuthorByTweetId(long tweetId) {
        Tweet tweet = tweetDao.findById(tweetId).orElseThrow(()-> new NotFoundException("tweet not found!", 40004L));
        return Optional.ofNullable(map.get(tweet.getAuthorId()));
    }
}
