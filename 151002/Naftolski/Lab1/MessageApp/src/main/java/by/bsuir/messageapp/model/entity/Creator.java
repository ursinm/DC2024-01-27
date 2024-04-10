package by.bsuir.messageapp.model.entity;

import by.bsuir.messageapp.model.AEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Creator extends AEntity {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
}
