package by.haritonenko.rest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Author extends AbstractEntity {

    private String login;
    private String password;
    private String firstname;
    private String lastname;
}
