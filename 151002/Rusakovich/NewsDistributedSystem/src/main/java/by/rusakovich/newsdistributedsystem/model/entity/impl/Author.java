package by.rusakovich.newsdistributedsystem.model.entity.impl;

import by.rusakovich.newsdistributedsystem.model.entity.IEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Author<Id> implements IEntity<Id> {

    private Id id;
    @Override
    public Id getId(){return id;}

    @Override
    public void setId(Id newId) {
        id = newId;
    }

    private String login;
    private String password;
    private String firstname;
    private String lastname;
}
