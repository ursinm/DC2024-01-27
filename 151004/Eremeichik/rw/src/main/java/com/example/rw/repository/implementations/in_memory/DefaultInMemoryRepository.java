package com.example.rw.repository.implementations.in_memory;

import com.example.rw.model.entity.interfaces.EntityModel;
import com.example.rw.repository.interfaces.EntityRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class DefaultInMemoryRepository<I, E extends EntityModel<I>> implements EntityRepository<I, E> {

    private final Map<I, E> repository;
    private final ReadWriteLock readWriteLock;

    protected DefaultInMemoryRepository() {
        this.repository = new HashMap<>();
        readWriteLock = new ReentrantReadWriteLock();
    }

    public abstract I incrementAndGetId();

    @Override
    public Optional<E> findById(I id) {
        readWriteLock.readLock().lock();
        try {
            if (repository.containsKey(id)) {
                return Optional.of(repository.get(id));
            }
        } finally {
            readWriteLock.readLock().unlock();
        }
        return Optional.empty();
    }

    @Override
    public List<E> findAll() {
        List<E> result;
        readWriteLock.readLock().lock();
        try {
            result = new ArrayList<>(repository.values());
        } finally {
            readWriteLock.readLock().unlock();
        }
        return result;
    }

    @Override
    public E save(E entity) {
        if(entity.getId() == null) {
            entity.setId(incrementAndGetId());
        }
        readWriteLock.writeLock().lock();
        try {
            repository.put(entity.getId(), entity);
        } finally {
            readWriteLock.writeLock().unlock();
        }
        return entity;
    }

    @Override
    public void deleteById(I id) {
        readWriteLock.writeLock().lock();
        try {
            repository.remove(id);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }
}
