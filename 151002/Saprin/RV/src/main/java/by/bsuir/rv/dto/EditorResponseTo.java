package by.bsuir.rv.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
public class EditorResponseTo {
    private BigInteger id;
    private String login;
    private String password;
    private String firstname;
    private String lastname;

    public EditorResponseTo(BigInteger id, String login, String password, String firstname, String lastname) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
