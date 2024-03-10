package by.bsuir.dc.exception.handler;

import by.bsuir.dc.dto.response.ErrorResponseDto;
import by.bsuir.dc.exception.ResourceNotFoundException;
import by.bsuir.dc.util.ErrorUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex){
        return ErrorUtil.errorResponseEntity(HttpStatus.NOT_FOUND, 1, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        return ErrorUtil.errorResponseEntity(HttpStatus.BAD_REQUEST, 3, errors.toString());
    }

//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<ErrorResponseDto> handleRuntimeException(RuntimeException ex){
//        return ErrorUtil.errorResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, 1,
//                "Internal server error. Try later again");
//    }
}
