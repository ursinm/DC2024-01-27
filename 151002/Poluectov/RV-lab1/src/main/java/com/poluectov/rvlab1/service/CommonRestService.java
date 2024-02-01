package com.poluectov.rvlab1.service;

import com.poluectov.rvlab1.model.IdentifiedEntity;
import com.poluectov.rvlab1.repository.ICommonRepository;

import java.math.BigInteger;
import java.util.List;

public abstract class CommonRestService<Entity extends IdentifiedEntity, Request extends IdentifiedEntity, Response extends IdentifiedEntity> {

    ICommonRepository<Entity, Request> repository;

    public CommonRestService(ICommonRepository<Entity, Request> repository) {
        this.repository = repository;
    }

    abstract Response mapResponseTo(Entity entity);

    public List<Response> all() {
        return repository.findAll().stream().map(this::mapResponseTo).toList();
    }

    public Response one(BigInteger id) {
        return mapResponseTo(repository.find(id));
    }

    public Response create(Request request) {
        Entity entity = repository.save(request);
        return mapResponseTo(entity);
    }

    public Response update(BigInteger id, Request request) {
        //repository updates by id
        request.setId(id);
        Entity entity = repository.save(request);
        return mapResponseTo(entity);
    }
    public void delete(BigInteger id) {
        repository.delete(id);
    }

}
