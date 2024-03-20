package by.rusakovich.newsdistributedsystem.model.entity.impl;

import by.rusakovich.newsdistributedsystem.model.entity.IEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Note<Id> implements IEntity<Id> {

    private Id id;
    @Override
    public Id getId(){return id;}

    @Override
    public void setId(Id newId) {
        id = newId;
    }
    private Long newsId;
    private String content;
}
