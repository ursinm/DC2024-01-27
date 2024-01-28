package by.bsuir.dc.rest_basics.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    private long id;

    private String login;

    private String password;

    private String firstName;

    private String lastName;

}
