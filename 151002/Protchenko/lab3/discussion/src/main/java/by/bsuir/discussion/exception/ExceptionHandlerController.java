package by.bsuir.discussion.exception;

import by.bsuir.discussion.model.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ErrorUtil.errorResponseEntity(HttpStatus.NOT_FOUND, 1, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getFieldErrors()
                .stream()
                .map(e -> e.getField() + " : " + e.getDefaultMessage())
                .toList();
        return ErrorUtil.errorResponseEntity(HttpStatus.BAD_REQUEST, 3,
                "Argument validation error: " + errors);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponseDto> handleRuntimeException(Throwable t){
        return ErrorUtil.errorResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, 1,
                "Internal server error. Try later again");
    }
}
