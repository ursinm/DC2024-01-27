package by.bsuir.dc.rest_basics.services.exceptions;

import lombok.Getter;

@Getter
public enum LabelSubCode {

    //422 - Unprocessable entity
    WRONG_NAME_LEN(1, "Label's name length must be from 2 to 32"),

    NO_NAME_PROVIDED(3, "No name provided");

    private final int subCode;

    private final String message;

    LabelSubCode(int subCode, String message) {
        this.subCode = subCode;
        this.message = message;
    }

}
