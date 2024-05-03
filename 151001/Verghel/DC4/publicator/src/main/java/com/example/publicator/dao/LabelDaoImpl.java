package com.example.publicator.dao;

import com.example.publicator.model.Label;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

//@Repository
public class LabelDaoImpl implements LabelDao {

    private static final Map<Integer, Label> map = new HashMap<>();
    private static final AtomicInteger idHolder = new AtomicInteger();


    @Override
    public Label create(Label entity) {
        int id = idHolder.incrementAndGet();
        entity.setId(id);
        map.put(id, entity);
        return entity;
    }

    @Override
    public List<Label> readAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Label read(int id) {
        return map.getOrDefault(id, null);
    }

    @Override
    public Label update(Label entity, int id) {
        if (map.containsKey(id)
        ) {
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
