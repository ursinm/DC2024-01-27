package by.rusakovich.newsdistributedsystem.model.dto.mapper;

import by.rusakovich.newsdistributedsystem.model.entity.IEntity;

import java.util.List;

public interface EntityMapper<Id, Entity extends IEntity<Id>, Request, Response> {

    Entity mapToEntity(Request request);
    List<Response> mapToResponseList(Iterable<Entity> entities);
    Response mapToResponse(Entity entity);
}
