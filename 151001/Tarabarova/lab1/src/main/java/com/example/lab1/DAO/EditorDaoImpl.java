package com.example.lab1.DAO;

import com.example.lab1.Model.Editor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class EditorDaoImpl implements EditorDao {
    private static final Map<Integer, Editor> map = new HashMap();
    private static final AtomicInteger idHolder = new AtomicInteger();


    @Override
    public Editor create(Editor entity) {
        int editorId = idHolder.incrementAndGet();
        entity.setId(editorId);
        map.put(editorId, entity);
        return entity;
    }

    @Override
    public List<Editor> readAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Editor read(int id) {
        return map.getOrDefault(id, null);
    }

    @Override
    public Editor update(Editor entity, int id) {
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
