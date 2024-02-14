package by.bsuir.dc.rest_basics.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorRequestTo {

    public AuthorRequestTo(String login, String password, String firstName, String lastName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private String login;

    private String password;

    private String firstName;

    private String lastName;

}
