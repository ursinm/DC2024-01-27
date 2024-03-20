package by.rusakovich.newsdistributedsystem.model.entity.impl;

import by.rusakovich.newsdistributedsystem.model.entity.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Author extends Entity {

    private String login;
    private String password;
    private String firstname;
    private String lastname;
}
