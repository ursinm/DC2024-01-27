package by.rusakovich.newsdistributedsystem.model.entity;

public interface IEntity<Id> {
    Id getId();
    void setId(Id newId);

}
