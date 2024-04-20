package by.bsuir.news.repository.implementations;

import by.bsuir.news.entity.IEntity;
import by.bsuir.news.repository.IMemoRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericRepositoryImplementation<T extends IEntity> implements IMemoRepository<T> {
    Map<Long, T> entities = new HashMap<>();
    @Override
    public List<T> findAll() {
        return new ArrayList<>(entities.values());
    }

    @Override
    public T findById(Long id) {
        if(!entities.containsKey(id)) {
            return null;
        }
        return entities.get(id);
    }

    @Override
    public Long save(T entity) {
        Long latestId = 0L;
        for(Map.Entry<Long, T> pair : entities.entrySet()) {
            Long key = pair.getKey();
            if(key > latestId) {
                latestId = key;
            }
        }
        entity.setId(++latestId);
        entities.put(latestId, entity);
        return latestId;
    }

    @Override
    public Long delete(Long id) {
        if(entities.isEmpty() || !entities.containsKey(id)) {
            return null;
        }
        entities.remove(id);
        return id;
    }

    @Override
    public Long update(Long id, T entity) {
        if(!entities.containsKey(id)) {
            return null;
        }
        entities.remove(id);
        entities.put(id, entity);
        return id;
    }
}
