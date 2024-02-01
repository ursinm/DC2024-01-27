package com.poluectov.rvlab1.repository.inmemory;

import com.poluectov.rvlab1.model.IdentifiedEntity;
import com.poluectov.rvlab1.utils.dtoconverter.DtoConverter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public abstract class InMemoryRepository<Entity extends IdentifiedEntity, Request extends IdentifiedEntity> {

    protected Map<BigInteger, Entity> data;
    BigInteger lastId = BigInteger.ONE;

    DtoConverter<Request, Entity> convert;

    public InMemoryRepository(DtoConverter<Request, Entity> convert) {
        data = new HashMap<>();
        this.convert = convert;
    }

    public List<Entity> findAll() {
        return data.values().stream().toList();
    }

    public Entity save(Request request) {
        if (request.getId() == null || request.getId().equals(BigInteger.ZERO)) {
            request.setId(lastId);
            lastId = lastId.add(BigInteger.ONE);
        }
        Entity entity = convert.convert(request);
        data.put(request.getId(), entity);
        return entity;
    }

    public void delete(BigInteger id) {
        data.remove(id);
    }

    public Entity find(BigInteger id) {
        return data.get(id);
    }

}
