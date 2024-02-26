package by.bsuir.taskrest.repository.implementations;

import by.bsuir.taskrest.entity.BaseEntity;
import jakarta.annotation.Nonnull;
import org.springframework.data.repository.ListCrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public abstract class InMemoryRepository<T extends BaseEntity<Long>> implements ListCrudRepository<T, Long> {

    private static final AtomicLong idCounter = new AtomicLong();
    protected final Map<Long, T> entities = new ConcurrentHashMap<>();

    @Override
    @Nonnull
    public <S extends T> S save(S entity) {
        if (entity.getId() == null) {
            entity.setId(idCounter.incrementAndGet());
        }
        entities.put(entity.getId(), entity);
        return entity;
    }

    @Override
    @Nonnull
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        entities.forEach(this::save);
        return new ArrayList<>((List<S>) entities);
    }

    @Override
    @Nonnull
    public Optional<T> findById(@Nonnull Long id) {
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public boolean existsById(@Nonnull Long id) {
        return entities.containsKey(id);
    }

    @Override
    @Nonnull
    public List<T> findAll() {
        return new ArrayList<>(entities.values());
    }

    @Override
    @Nonnull
    public List<T> findAllById(@Nonnull Iterable<Long> ids) {
        return entities.values()
                .stream()
                .filter(entity -> {
                    for (Long id : ids) {
                        if (entity.getId().equals(id)) {
                            return true;
                        }
                    }
                    return false;
                }).toList();
    }

    @Override
    public long count() {
        return entities.size();
    }

    @Override
    public void deleteById(@Nonnull Long id) {
        entities.remove(id);
    }

    @Override
    public void delete(@Nonnull T entity) {
        entities.remove(entity.getId());
    }

    @Override
    public void deleteAllById(@Nonnull Iterable<? extends Long> ids) {
        ids.forEach(entities::remove);
    }

    @Override
    public void deleteAll(@Nonnull Iterable<? extends T> entities) {
        entities.forEach(entity -> this.entities.remove(entity.getId()));
    }

    @Override
    public void deleteAll() {
        entities.clear();
    }
}
