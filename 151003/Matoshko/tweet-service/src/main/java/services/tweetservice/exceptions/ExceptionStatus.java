package services.tweetservice.exceptions;

import jakarta.validation.ConstraintViolationException;
import lombok.Getter;
import org.springframework.dao.DataIntegrityViolationException;

@Getter
public enum ExceptionStatus {
    NO_SUCH_CREATOR_EXCEPTION_STATUS("00", NoSuchCreatorException.class),
    VALIDATION_ERROR_EXCEPTION_STATUS("01", ConstraintViolationException.class),
    DB_CONSTRAINTS_EXCEPTION_STATUS("02", DataIntegrityViolationException.class);
    private final String value;
    private final Class<? extends Exception> exception;

    ExceptionStatus(String value, Class<? extends Exception> exception) {
        this.value = value;
        this.exception = exception;
    }
}
