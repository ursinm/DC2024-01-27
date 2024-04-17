package by.rusakovich.publisher.model.dto.mapper;

import by.rusakovich.publisher.model.entity.IEntity;

@SuppressWarnings("EmptyMethod")
public interface EntityMapper<Id, Entity extends IEntity<Id>, Request, Response> {

    Entity mapToEntity(Request request) throws ConversionError;
    Response mapToResponse(Entity entity) throws ConversionError;
}
