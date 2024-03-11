package com.example.rw.service.db_operations.interfaces;

import com.example.rw.exception.model.not_found.EntityNotFoundException;
import com.example.rw.model.entity.interfaces.EntityModel;

import java.util.List;

public interface EntityService<I, E extends EntityModel<I>> {

    E findById(I id) throws EntityNotFoundException;

    List<E> findAll();

    void save(E entity);

    void deleteById(I id) throws EntityNotFoundException;

    void update(E entity);
}
