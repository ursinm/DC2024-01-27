package services.tweetservice.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionsHandler {
    @ExceptionHandler(NoSuchCreatorException.class)
    public ResponseEntity<ErrorResponseTo> handleNotFoundException(NoSuchCreatorException exception) {
        ErrorResponseTo errorResponseTo = new ErrorResponseTo(exception.getMessage(), HttpStatus.NOT_FOUND + ExceptionStatus.NO_SUCH_CREATOR_EXCEPTION_STATUS.getValue());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseTo);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseTo> handleConstraintViolationException(ConstraintViolationException exception) {
        ErrorResponseTo errorResponseTo = new ErrorResponseTo(exception.getMessage(), HttpStatus.BAD_REQUEST.value() + ExceptionStatus.VALIDATION_ERROR_EXCEPTION_STATUS.getValue());
        return new ResponseEntity<>(errorResponseTo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseTo> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        ErrorResponseTo errorResponseTo = new ErrorResponseTo(exception.getMessage(), HttpStatus.FORBIDDEN.value() + ExceptionStatus.DB_CONSTRAINTS_EXCEPTION_STATUS.getValue());
        return new ResponseEntity<>(errorResponseTo, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoSuchTweetException.class)
    public ResponseEntity<ErrorResponseTo> handleNoSuchTweetException(NoSuchTweetException exception) {
        ErrorResponseTo errorResponseTo = new ErrorResponseTo(exception.getMessage(), HttpStatus.NOT_FOUND + ExceptionStatus.NO_SUCH_TWEET_EXCEPTION_STATUS.getValue());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseTo);
    }

    @ExceptionHandler(NoSuchMessageException.class)
    public ResponseEntity<ErrorResponseTo> handleNoSuchMessageException(NoSuchMessageException exception) {
        ErrorResponseTo errorResponseTo = new ErrorResponseTo(exception.getMessage(), HttpStatus.NOT_FOUND + ExceptionStatus.NO_SUCH_MESSAGE_EXCEPTION_STATUS.getValue());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseTo);
    }

    @ExceptionHandler(NoSuchStickerException.class)
    public ResponseEntity<ErrorResponseTo> handleNoSuchStickerException(NoSuchStickerException exception) {
        ErrorResponseTo errorResponseTo = new ErrorResponseTo(exception.getMessage(), HttpStatus.NOT_FOUND + ExceptionStatus.NO_SUCH_STICKER_EXCEPTION_STATUS.getValue());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseTo);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponseTo> handleServiceException(ServiceException exception) {
        ErrorResponseTo errorResponseTo = new ErrorResponseTo(exception.getErrorDto().errorMessage(), exception.getErrorDto().errorCode());
        return ResponseEntity.status(HttpStatus.valueOf((exception.getErrorDto().errorCode()))).body(errorResponseTo);
    }
}
