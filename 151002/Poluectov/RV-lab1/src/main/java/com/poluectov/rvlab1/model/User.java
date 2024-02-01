package com.poluectov.rvlab1.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class User extends IdentifiedEntity {

    private String login;
    private String password;
    private String firstName;
    private String lastName;

}