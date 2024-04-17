package by.rusakovich.publisher.dao.memory;

import by.rusakovich.publisher.model.entity.IEntity;

import java.util.concurrent.atomic.AtomicLong;

public class MemoryEntityRepositoryLongId<Entity extends IEntity<Long>> extends MemoryEntityRepository<Long, Entity> {
    private final AtomicLong id = new AtomicLong();
    @Override
    public Long getNewId() {
        return id.incrementAndGet();
    }
}
