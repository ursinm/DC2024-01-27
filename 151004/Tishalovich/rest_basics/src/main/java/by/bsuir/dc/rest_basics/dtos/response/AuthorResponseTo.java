package by.bsuir.dc.rest_basics.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorResponseTo {

    private long id;

    private String login;

    private String firstName;

    private String lastName;

}
