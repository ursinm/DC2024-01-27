package by.bsuir.rv.dto;

import by.bsuir.rv.bean.IdentifiedBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class EditorResponseTo extends IdentifiedBean {
    private String login;
    private String password;
    private String firstname;
    private String lastname;

    public EditorResponseTo(BigInteger id, String login, String password, String firstname, String lastname) {
        super(id);
        this.login = login;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
