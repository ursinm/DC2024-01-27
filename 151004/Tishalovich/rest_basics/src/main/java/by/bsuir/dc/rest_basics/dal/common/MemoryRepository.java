package by.bsuir.dc.rest_basics.dal.common;

import by.bsuir.dc.rest_basics.entities.common.AbstractEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public abstract class MemoryRepository<T extends AbstractEntity> implements CrudRepository<T, Long> {

    private final static AtomicLong ids = new AtomicLong();

    protected final Map<Long, T> map = new HashMap<>();

    @Override
    public Iterable<T> getAll() {
        return map.values();
    }

    @Override
    public Optional<T> getById(Long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Optional<T> save(T entity) {
        Long id = ids.incrementAndGet();
        entity.setId(id);
        map.put(id, entity);
        return Optional.of(entity);
    }

    @Override
    public Optional<T> delete(T entity) {
        return Optional.ofNullable(map.remove(entity.getId()));
    }

    public Optional<T> delete(Long id) {
        return Optional.ofNullable(map.remove(id));
    }
}
