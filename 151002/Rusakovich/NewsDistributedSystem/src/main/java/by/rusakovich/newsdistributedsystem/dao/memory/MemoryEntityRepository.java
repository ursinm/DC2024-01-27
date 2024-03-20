package by.rusakovich.newsdistributedsystem.dao.memory;

import by.rusakovich.newsdistributedsystem.dao.IEntityRepository;
import by.rusakovich.newsdistributedsystem.model.entity.IEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class MemoryEntityRepository<Id, Entity extends IEntity<Id>> implements IEntityRepository <Id, Entity>{
    private final Map<Id, Entity> rep = new HashMap<>();
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public abstract Id getNewId();

    @Override
    public Optional<Entity> readById(Id id) {
        rwLock.readLock().lock();
        try {
            if(rep.containsKey(id)){
                return Optional.ofNullable(rep.get(id));
            }
        } finally {
           rwLock.readLock().unlock();
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(Id id) {
        rwLock.writeLock().lock();
        try {
            rep.remove(id);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    @Override
    public Optional<Entity> create(Entity entity) {
        if(entity.getId() == null){
            entity.setId(getNewId());
        }
        rwLock.writeLock().lock();
        try {
            rep.put(entity.getId(), entity);
        } finally {
            rwLock.writeLock().unlock();
        }
        // for consistency
        return Optional.of(entity);
    }

    @Override
    public Iterable<Entity> readAll() {
        rwLock.readLock().lock();
        Iterable<Entity> result;
        try {
            result = rep.values();
        } finally {
            rwLock.readLock().unlock();
        }
        return result;
    }
}
