package by.bsuir.dc.rest_basics.services.exceptions;

import lombok.Getter;

@Getter
public enum AuthorSubCode {
    //422 - Unprocessable entity
    WRONG_LOGIN_LEN(1),
    WRONG_PASSWORD_LEN(2),
    WRONG_FIRST_NAME_LEN(3),
    WRONG_LAST_NAME_LEN(4),

    NO_LOGIN_PROVIDED(5),
    NO_PASSWORD_PROVIDED(6),
    NO_FIRST_NAME_PROVIDED(7),
    NO_LAST_NAME_PROVIDED(8),

    //404 - Not found
    WRONG_ID(1);

    private final int subCode;

    AuthorSubCode(int subCode) {
        this.subCode = subCode;
    }

}
