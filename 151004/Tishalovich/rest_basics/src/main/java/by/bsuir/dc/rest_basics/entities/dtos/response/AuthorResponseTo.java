package by.bsuir.dc.rest_basics.entities.dtos.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponseTo {

    private Long id;

    private String login;

    private String firstName;

    private String lastName;

}
