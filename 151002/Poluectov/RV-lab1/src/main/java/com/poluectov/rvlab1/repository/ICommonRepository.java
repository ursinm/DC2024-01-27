package com.poluectov.rvlab1.repository;

import com.poluectov.rvlab1.model.IdentifiedEntity;

import java.math.BigInteger;
import java.util.List;

public interface ICommonRepository<Entity extends IdentifiedEntity, Request extends IdentifiedEntity> {

    List<Entity> findAll();

    /**
     * Saves new if id is null
     * Updates existing if id is not null by current id
     * @param entity is either new or existing
     */
    Entity save(Request entity);
    void delete(BigInteger id);

    Entity find(BigInteger id);
}
