package by.bsuir.vladislavmatsiushenko.dao;

import by.bsuir.vladislavmatsiushenko.model.Issue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class IssueDaoImpl implements IssueDao {
    Map<Integer, Issue> map = new HashMap<>();
    private static final AtomicInteger idHolder = new AtomicInteger();

    @Override
    public Issue create(Issue entity) {
        int id = idHolder.incrementAndGet();
        entity.setId(id);
        map.put(id, entity);

        return entity;
    }

    @Override
    public Issue read(int id) {
        return map.getOrDefault(id, null);
    }

    @Override
    public List<Issue> readAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Issue update(Issue entity, int id) {
        if (map.containsKey(id)) {
            entity.setId(id);
            map.put(id, entity);

            return entity;
        }

        return null;
    }

    @Override
    public boolean delete(int id) {
        if (map.containsKey(id)) {
            map.remove(id);
            return true;
        }

        return false;
    }
}
