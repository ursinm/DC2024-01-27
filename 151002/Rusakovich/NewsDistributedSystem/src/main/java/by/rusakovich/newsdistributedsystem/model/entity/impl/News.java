package by.rusakovich.newsdistributedsystem.model.entity.impl;

import by.rusakovich.newsdistributedsystem.model.entity.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class News extends Entity {

    private Long authorId;
    private String title;
    private String content;
    private Date creation;
    private Date modification;
}
