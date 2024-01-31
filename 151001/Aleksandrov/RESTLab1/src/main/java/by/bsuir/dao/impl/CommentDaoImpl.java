package by.bsuir.dao.impl;

import by.bsuir.dao.CommentDao;
import by.bsuir.entities.Comment;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CommentDaoImpl implements CommentDao {
    private long counter = 0;
    private final Map<Long, Comment> map = new HashMap<>();
    @Override
    public Comment save(Comment entity) {
        counter++;
        map.put(counter, entity);
        entity.setId(counter);
        return entity;
    }

    @Override
    public void delete(long id) {
        map.remove(id);
    }

    @Override
    public List<Comment> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Comment update(Comment entity, long id) {
        entity.setId(id);
        map.put(id, entity);
        return entity;
    }
}
