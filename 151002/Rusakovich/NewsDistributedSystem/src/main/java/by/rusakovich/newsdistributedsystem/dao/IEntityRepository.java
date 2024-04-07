package by.rusakovich.newsdistributedsystem.dao;

import by.rusakovich.newsdistributedsystem.model.entity.IEntity;

import java.util.Optional;

public interface IEntityRepository <Id, Entity extends IEntity<Id>>{
    Optional<Entity> readById(Id id);
    void removeById(Id id);
    Optional<Entity> create(Entity entity);
    Optional<Entity> update(Entity entity);
    Iterable<Entity> readAll();
}
