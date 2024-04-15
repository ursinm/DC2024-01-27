package by.bsuir.dc.publisher.services.exceptions;

import lombok.Getter;

@Getter
public enum AuthorSubCode {

    //422 - Unprocessable entity
    WRONG_LOGIN_LEN(1, "Author's login len must be from 2 to 64"),
    WRONG_PASSWORD_LEN(2, "Author's password len must be from 8 to 128"),
    WRONG_FIRST_NAME_LEN(3, "Author's first name len must be from 2 to 64"),
    WRONG_LAST_NAME_LEN(4, "Author's last name len must be from 2 to 64"),

    NO_LOGIN_PROVIDED(5, "No login provided"),
    NO_PASSWORD_PROVIDED(6, "No password provided"),
    NO_FIRST_NAME_PROVIDED(7, "No firstName provided"),
    NO_LAST_NAME_PROVIDED(8, "No lastName provided"),

    NO_AUTHOR_FOR_STORY(9, "No author found for this story");

    private final int subCode;

    private final String message;

    AuthorSubCode(int subCode, String message) {
        this.subCode = subCode;
        this.message = message;
    }

}
