package by.rusakovich.newsdistributedsystem.service;

import by.rusakovich.newsdistributedsystem.dao.IEntityRepository;
import by.rusakovich.newsdistributedsystem.model.dto.mapper.EntityMapper;
import by.rusakovich.newsdistributedsystem.model.entity.IEntity;
import by.rusakovich.newsdistributedsystem.service.exception.CantCreate;
import by.rusakovich.newsdistributedsystem.service.exception.NotFound;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public abstract class EntityService<Id, RequestTO, ResponseTO, Entity extends IEntity<Id>> implements IEntityService<Id, RequestTO, ResponseTO> {

    private EntityMapper<Id, Entity, RequestTO, ResponseTO> mapper;
    private IEntityRepository<Id, Entity> rep;

    @Override
    public ResponseTO readById(Id id) {
        var entity = rep.readById(id).orElseThrow(() -> new NotFound(id));
        return mapper.mapToResponse(entity);
    }

    @Override
    public List<ResponseTO> readAll() {
        var entities = rep.readAll();
        return mapper.mapToResponseList(entities);
    }

    @Override
    public ResponseTO create(RequestTO newEntity) {
        var mappedNewEntity= mapper.mapToEntity(newEntity);
        var entity = rep.create(mappedNewEntity).orElseThrow(CantCreate::new);
        return mapper.mapToResponse(entity);
    }

    @Override
    public ResponseTO update(RequestTO updatedEntity) {
        var mappedEntityToUpdate = mapper.mapToEntity(updatedEntity);
        var entity = rep.update(mappedEntityToUpdate).orElseThrow(() -> new NotFound(mappedEntityToUpdate.getId()));
        return mapper.mapToResponse(entity);
    }

    @Override
    public void deleteById(Id id) {
        if(!rep.deleteById(id)) throw new NotFound(id);
    }
}
