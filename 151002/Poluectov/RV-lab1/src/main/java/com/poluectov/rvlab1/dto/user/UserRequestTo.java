package com.poluectov.rvlab1.dto.user;

import com.poluectov.rvlab1.model.IdentifiedEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestTo extends IdentifiedEntity {
    String login;
    String password;
    String firstName;
    String lastName;
}
