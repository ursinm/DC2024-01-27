package com.example.lab2.DAO;

import com.example.lab2.Model.Sticker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

//@Repository
public class StickerDaoImpl implements StickerDao {

    private static final Map<Integer, Sticker> map = new HashMap<>();
    private static final AtomicInteger idHolder = new AtomicInteger();


    @Override
    public Sticker create(Sticker entity) {
        int id = idHolder.incrementAndGet();
        entity.setId(id);
        map.put(id, entity);
        return entity;
    }

    @Override
    public List<Sticker> readAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Sticker read(int id) {
        return map.getOrDefault(id, null);
    }

    @Override
    public Sticker update(Sticker entity, int id) {
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
