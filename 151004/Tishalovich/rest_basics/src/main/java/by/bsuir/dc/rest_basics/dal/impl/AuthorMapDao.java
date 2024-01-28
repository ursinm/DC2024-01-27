package by.bsuir.dc.rest_basics.dal.impl;

import by.bsuir.dc.rest_basics.dal.AuthorDao;
import by.bsuir.dc.rest_basics.entities.Author;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class AuthorMapDao implements AuthorDao {

    private long counter = 0;

    private final Map<Long, Author> map = new HashMap<>();

    @Override
    public Author save(Author entity) {
        counter++;
        map.put(counter, entity);
        entity.setId(counter);
        return entity;
    }

    @Override
    public Author delete(long id) {
        return map.remove(id);
    }

    @Override
    public Iterable<Author> findAll() {
        return map.values();
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Author updateById(Author entity, long id) {
        entity.setId(id);
        map.put(id, entity);
        return entity;
    }

    @Override
    public boolean existsById(long id) {
        return findById(id).isPresent();
    }

}
