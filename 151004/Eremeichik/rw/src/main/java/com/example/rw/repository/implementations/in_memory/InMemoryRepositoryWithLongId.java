package com.example.rw.repository.implementations.in_memory;

import com.example.rw.model.entity.interfaces.EntityModel;

import java.util.concurrent.atomic.AtomicLong;

public abstract class InMemoryRepositoryWithLongId<E extends EntityModel<Long>> extends DefaultInMemoryRepository<Long,E> {

    private final AtomicLong currentId;

    protected InMemoryRepositoryWithLongId() {
        this.currentId = new AtomicLong();
    }

    @Override
    public Long incrementAndGetId() {
        return currentId.incrementAndGet();
    }
}
