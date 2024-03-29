package by.rusakovich.newsdistributedsystem.dao.memory;

import by.rusakovich.newsdistributedsystem.model.entity.IEntity;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryEntityRepositoryLongId<Entity extends IEntity<Long>> extends MemoryEntityRepository<Long, Entity> {
    private final AtomicLong id = new AtomicLong(10);//new Random().nextLong());
    @Override
    public Long getNewId() {
        return id.incrementAndGet();
    }
}
