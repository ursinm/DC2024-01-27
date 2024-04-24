package com.example.lab1.dao;

import com.example.lab1.model.Issue;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
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
    public List<Issue> readAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Issue read(int id) {
        return map.getOrDefault(id, null);
    }

    @Override
    public Issue update(Issue entity, int id) {
        if (map.containsKey(id)) {
            entity.setId(id);
            map.put(id, entity);
            return entity;
        } else
            return null;
    }

    @Override
    public boolean delete(int id) {
        if (map.containsKey(id)) {
            map.remove(id);
            return true;
        } else
            return false;
    }
}
