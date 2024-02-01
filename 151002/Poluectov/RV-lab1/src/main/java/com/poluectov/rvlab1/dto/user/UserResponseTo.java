package com.poluectov.rvlab1.dto.user;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.poluectov.rvlab1.model.IdentifiedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@JsonRootName("user")
public class UserResponseTo extends IdentifiedEntity {

    private String login;
    private String hashedPassword;
    private String firstName;
    private String lastName;
}
