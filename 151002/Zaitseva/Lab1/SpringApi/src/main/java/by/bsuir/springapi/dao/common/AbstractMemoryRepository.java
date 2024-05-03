package by.bsuir.springapi.dao.common;

import by.bsuir.springapi.model.AbstractEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public abstract class AbstractMemoryRepository<T extends AbstractEntity> implements CrudRepository<Long, T> {
    private final AtomicLong ids = new AtomicLong();
    
    protected final Map<Long, T> map = new HashMap<>();
    
    @Override
    public Optional<T> getBy(Long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Iterable<T> getAll() {
        return map.values();
    }

    @Override
    public Optional<T> save(T entity) {
        long id = ids.incrementAndGet();
        entity.setId(id);
        map.put(id, entity);
        return Optional.of(entity);
    }

    @Override
    public boolean remove(T entity) {
        return map.remove(entity.getId(), entity);
    }

    @Override
    public boolean removeById(Long id) {
        return map.remove(id, map.get(id));
    }

    @Override
    public Optional<T> update(T entity) {
        map.put(entity.getId(), entity);
        return Optional.of(entity);
    }
}
