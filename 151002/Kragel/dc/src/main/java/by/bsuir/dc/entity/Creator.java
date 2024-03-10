package by.bsuir.dc.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Getter
@Setter
public class Creator extends AbstractEntity {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
}
