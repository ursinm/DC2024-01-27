package com.example.lab1.DAO;

import com.example.lab1.Model.Story;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class StoryDaoImpl implements StoryDao {
    Map<Integer, Story> map = new HashMap<>();
    private static final AtomicInteger idHolder = new AtomicInteger();

    @Override
    public Story create(Story entity) {
        int id = idHolder.incrementAndGet();
        entity.setId(id);
        map.put(id, entity);
        return entity;
    }

    @Override
    public List<Story> readAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Story read(int id) {
        return map.getOrDefault(id, null);
    }

    @Override
    public Story update(Story entity, int id) {
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
