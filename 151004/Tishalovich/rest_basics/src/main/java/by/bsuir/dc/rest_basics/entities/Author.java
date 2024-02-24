package by.bsuir.dc.rest_basics.entities;

import by.bsuir.dc.rest_basics.entities.common.AbstractEntity;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Author extends AbstractEntity {

    private String login;

    private String password;

    private String firstName;

    private String lastName;

    public Author(Long id, String login, String password, String firstName, String lastName) {
        setId(id);
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
