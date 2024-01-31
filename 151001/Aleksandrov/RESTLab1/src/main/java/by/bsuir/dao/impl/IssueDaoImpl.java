package by.bsuir.dao.impl;

import by.bsuir.dao.IssueDao;
import by.bsuir.entities.Issue;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class IssueDaoImpl implements IssueDao {

    private long counter = 0;
    private final Map<Long, Issue> map = new HashMap<>();
    @Override
    public Issue save(Issue entity) {
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
    public List<Issue> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<Issue> findById(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Issue update(Issue entity, long id) {
        entity.setId(id);
        map.put(id, entity);
        return entity;
    }
}
