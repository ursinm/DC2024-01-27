package by.bsuir.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionApiHandler {

    private static final Long ERROR_CODE = 4001L;

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> catchValidationException(ConstraintViolationException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(ERROR_CODE, "Bad request!"));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> catchNotFoundException(NotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getStatus(), exception.getMessage()));
    }

    @ExceptionHandler(UpdateException.class)
    public ResponseEntity<ErrorMessage> catchUpdateException(UpdateException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getStatus(), exception.getMessage()));
    }

    @ExceptionHandler(DeleteException.class)
    public ResponseEntity<ErrorMessage> catchDeleteException(DeleteException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getStatus(), exception.getMessage()));
    }
}
