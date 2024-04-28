package by.bsuir.vladislavmatsiushenko.impl.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponseTo {
    private long id;
    private String login;
    private String password;
    private String firstname;
    private String lastname;
}
