package by.bsuir.dc.repository.common;

import by.bsuir.dc.entity.AbstractEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public abstract class InMemoryCrudRepository<T extends AbstractEntity> implements CrudRepository<T, Long> {

    private final static AtomicLong ids = new AtomicLong();

    protected final Map<Long, T> map = new HashMap<>();

    @Override
    public <S extends T> S save(S entity) {
        Long id = entity.getId();
        if (map.get(id) == null) {
            id = ids.incrementAndGet();
            entity.setId(id);
        }
        map.put(id, entity);
        return entity;
    }

    @Override
    public Optional<T> getById(Long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Iterable<T> getAll() {
        return map.values();
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
