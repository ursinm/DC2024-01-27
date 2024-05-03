package application.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorResponseTo implements Serializable {
    private Long id;
    private String login;
    private String password;
    private String firstname;
    private String lastname;
}
