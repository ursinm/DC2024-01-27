package service.tweetservicediscussion.exceptions;

import jakarta.validation.ConstraintViolationException;
import lombok.Getter;
import org.springframework.dao.DataIntegrityViolationException;

@Getter
public enum ExceptionStatus {
    NO_SUCH_MESSAGE_EXCEPTION_STATUS("02", NoSuchMessageException.class),
    VALIDATION_ERROR_EXCEPTION_STATUS("04", ConstraintViolationException.class),
    DB_CONSTRAINTS_EXCEPTION_STATUS("05", DataIntegrityViolationException.class);
    private final String value;
    private final Class<? extends Exception> exception;

    ExceptionStatus(String value, Class<? extends Exception> exception) {
        this.value = value;
        this.exception = exception;
    }
}
