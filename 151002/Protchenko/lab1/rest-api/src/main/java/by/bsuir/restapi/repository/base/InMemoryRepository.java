package by.bsuir.restapi.repository.base;

import by.bsuir.restapi.model.entity.Entity;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public abstract class InMemoryRepository<T extends Entity<Long>> implements CrudRepository<T, Long> {

    private final static AtomicLong counter = new AtomicLong();

    protected final Map<Long, T> map = new HashMap<>();

    @Override
    public <S extends T> S save(S entity) {
        Long id = entity.getId();
        if (map.get(id) == null) {
            id = counter.incrementAndGet();
            entity.setId(id);
        }
        map.put(id, entity);
        return entity;
    }

    @Override
    public Optional<T> findById(Long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public void deleteById(Long id) {
        map.remove(id);
    }

    @Override
    public void delete(T entity) {
        map.remove(entity.getId());
    }

}
