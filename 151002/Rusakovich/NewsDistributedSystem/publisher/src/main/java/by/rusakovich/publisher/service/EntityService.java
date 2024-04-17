package by.rusakovich.publisher.service;

import by.rusakovich.publisher.dao.IEntityRepository;
import by.rusakovich.publisher.model.dto.mapper.EntityMapper;
import by.rusakovich.publisher.model.entity.IEntity;
import by.rusakovich.publisher.service.exception.CantCreate;
import by.rusakovich.publisher.service.exception.NotFound;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
        return StreamSupport.stream(rep.readAll().spliterator(), false)
                .map(mapper::mapToResponse)
                .collect(Collectors.toList());
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
        rep.removeById(id);
    }
}
