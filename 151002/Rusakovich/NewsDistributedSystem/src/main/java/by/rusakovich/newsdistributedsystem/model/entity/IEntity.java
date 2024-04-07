package by.rusakovich.newsdistributedsystem.model.entity;

import jakarta.persistence.Entity;

import java.io.Serializable;

public interface IEntity<Id> {
    Id getId();
    void setId(Id newId);

}
