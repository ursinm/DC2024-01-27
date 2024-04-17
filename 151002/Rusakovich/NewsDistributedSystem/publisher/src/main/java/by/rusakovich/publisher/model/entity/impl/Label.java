package by.rusakovich.publisher.model.entity.impl;

import by.rusakovich.publisher.model.entity.IEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@MappedSuperclass
public class Label<Id extends Serializable> implements IEntity<Id> {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Id id;
    @Override
    public Id getId(){return id;}

    @Override
    public void setId(Id newId) {
        id = newId;
    }

    @Column(length = 32)
    private String name;
}
