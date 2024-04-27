package by.bsuir.dc.discussion.service.exception;

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
