package by.bsuir.dao.impl;

import by.bsuir.dao.EditorDao;
import by.bsuir.entities.Editor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class EditorDaoImpl implements EditorDao {

    private long counter = 0;
    private final Map<Long, Editor> map = new HashMap<>();

    @Override
    public Editor save(Editor entity) {
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
    public List<Editor> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<Editor> findById(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Editor update(Editor entity, long id) {
        entity.setId(id);
        map.put(id, entity);
        return entity;
    }
}
