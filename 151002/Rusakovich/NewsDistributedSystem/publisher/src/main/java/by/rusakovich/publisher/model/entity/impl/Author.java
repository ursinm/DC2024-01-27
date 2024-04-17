package by.rusakovich.publisher.model.entity.impl;

import by.rusakovich.publisher.model.entity.IEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@MappedSuperclass
public class Author<Id extends Serializable> implements IEntity<Id> {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @jakarta.persistence.Id
    private Id id;
    @Override
    public Id getId(){return id;}

    @Override
    public void setId(Id newId) {
        id = newId;
    }

    @Column(unique = true, length = 64)
    private String login;

    @Column(length = 128)
    private String password;

    @Column(length = 64)
    private String firstname;

    @Column(length = 64)
    private String lastname;
}
