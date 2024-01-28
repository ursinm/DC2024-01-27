package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Editor {
    Long id;
    String login;
    String password;
    String firstname;
    String lastname;
}
