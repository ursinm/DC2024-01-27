package by.bsuir.rv.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Editor extends IdentifiedBean {
    private String login;
    private String password;
    private String firstname;
    private String lastname;

    public Editor(BigInteger id, String login, String password, String firstname, String lastname) {
        super(id);
        this.login = login;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
