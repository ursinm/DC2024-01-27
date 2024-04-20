package com.example.discussion.service.db_operations.interfaces;

import com.example.discussion.exception.model.not_found.EntityNotFoundException;
import com.example.discussion.model.entity.interfaces.EntityModel;

import java.util.List;

public interface EntityService<I, E extends EntityModel<I>> {

    E findById(I id) throws EntityNotFoundException;

    List<E> findAll();

    void save(E entity);

    void deleteById(I id) throws EntityNotFoundException;

    void update(E entity);
}
