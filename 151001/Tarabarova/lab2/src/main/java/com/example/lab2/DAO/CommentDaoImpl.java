package com.example.lab2.DAO;

import com.example.lab2.Model.Comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

//@Repository
public class CommentDaoImpl implements CommentDao {

    Map<Integer, Comment> map = new HashMap<>();
    private static final AtomicInteger idHolder = new AtomicInteger();

    @Override
    public Comment create(Comment entity) {
        int id = idHolder.incrementAndGet();
        entity.setId(id);
        map.put(id, entity);
        return entity;
    }

    @Override
    public List<Comment> readAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Comment read(int id) {
        return map.getOrDefault(id, null);
    }

    @Override
    public Comment update(Comment entity, int id) {
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
