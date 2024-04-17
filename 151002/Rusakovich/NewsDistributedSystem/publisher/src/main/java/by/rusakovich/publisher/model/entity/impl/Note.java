package by.rusakovich.publisher.model.entity.impl;

import by.rusakovich.publisher.model.entity.IEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@MappedSuperclass
public class Note<Id extends Serializable> implements IEntity<Id> {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @jakarta.persistence.Id
    private Id id;
    @Override
    public Id getId(){return id;}

    @Override
    public void setId(Id newId) {
        id = newId;
    }

    @Column(length = 2048)
    private String content;
}
