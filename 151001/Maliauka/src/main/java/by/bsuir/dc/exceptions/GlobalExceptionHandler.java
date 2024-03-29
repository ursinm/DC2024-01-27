package by.bsuir.dc.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ErrorMessage handleEntityAlreadyExistsException(EntityAlreadyExistsException exception) {
        return new ErrorMessage(
                exception.getMessage(),
                40000L
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessRuleException.class)
    public ErrorMessage handleBusinessRuleException(BusinessRuleException exception) {
        return new ErrorMessage(
                exception.getMessage(),
                40000L
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorMessage handleEntityNotFoundException(EntityNotFoundException exception) {
        return new ErrorMessage(
                exception.getMessage(),
                40400L
        );
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorMessage onConstraintValidationException(ConstraintViolationException exception) {
        return new ErrorMessage(
                exception.getMessage(),
                40400L
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorMessage onMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return new ErrorMessage(
                exception.getMessage(),
                40400L
        );
    }
}
