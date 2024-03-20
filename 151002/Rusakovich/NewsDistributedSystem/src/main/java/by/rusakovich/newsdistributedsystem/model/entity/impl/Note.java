package by.rusakovich.newsdistributedsystem.model.entity.impl;

import by.rusakovich.newsdistributedsystem.model.entity.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Note extends Entity {

    private Long newsId;
    private String content;
}
