package by.bsuir.restapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author implements Entity<Long> {

    private Long id;
    private String login;
    private String password;
    private String firstname;
    private String lastname;

}
