package by.bsuir.dc.rest_basics.services.exceptions;

import lombok.Getter;

@Getter
public enum GeneralSubCode {

    WRONG_ID(1, "There is no entity with such id");

    private final int subCode;

    private final String message;

    GeneralSubCode(int subCode, String message) {
        this.subCode = subCode;
        this.message = message;
    }

}
