package by.rusakovich.newsdistributedsystem.model.dto.mapper;

import by.rusakovich.newsdistributedsystem.model.entity.IEntity;

import java.util.List;

@SuppressWarnings("EmptyMethod")
public interface EntityMapper<Id, Entity extends IEntity<Id>, Request, Response> {

    Entity mapToEntity(Request request) throws ConversionError;
    Response mapToResponse(Entity entity) throws ConversionError;
}
