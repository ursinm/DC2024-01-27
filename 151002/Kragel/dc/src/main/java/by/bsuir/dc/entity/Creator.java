package by.bsuir.dc.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Creator extends AbstractEntity {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
}
