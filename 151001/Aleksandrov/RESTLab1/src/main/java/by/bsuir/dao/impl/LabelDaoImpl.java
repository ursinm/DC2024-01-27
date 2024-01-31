package by.bsuir.dao.impl;

import by.bsuir.dao.LabelDao;
import by.bsuir.entities.Label;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class LabelDaoImpl implements LabelDao {

    private long counter = 0;
    private final Map<Long, Label> map = new HashMap<>();
    @Override
    public Label save(Label entity) {
        counter++;
        map.put(counter, entity);
        entity.setId(counter);
        return entity;
    }
    @Override
    public void delete(long id){
        map.remove(id);
    }

    @Override
    public List<Label> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<Label> findById(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Label update(Label entity, long id) {
        entity.setId(id);
        map.put(id, entity);
        return entity;
    }
}
