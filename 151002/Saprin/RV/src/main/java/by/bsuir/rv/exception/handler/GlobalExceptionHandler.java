package by.bsuir.rv.exception.handler;

import by.bsuir.rv.exception.ApiError;
import by.bsuir.rv.exception.DuplicateEntityException;
import by.bsuir.rv.exception.EntititesNotFoundException;
import by.bsuir.rv.exception.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Integer ENTITY_NOT_FOUND_CODE = 1;
    private final Integer ENTITIES_NOT_FOUND_CODE = 2;

    @ExceptionHandler({EntityNotFoundException.class, EntititesNotFoundException.class, DuplicateEntityException.class})
    public final ResponseEntity<ApiError> handleException(Exception exception, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();


        if (exception instanceof EntityNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            ApiError apiError = new ApiError(exception.getMessage(), HttpStatus.NOT_FOUND.value() * 100 + ENTITY_NOT_FOUND_CODE);
            return new ResponseEntity<>(apiError, headers, status);
        }

        if (exception instanceof EntititesNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            ApiError apiError = new ApiError(exception.getMessage(), HttpStatus.NOT_FOUND.value() * 100 + ENTITIES_NOT_FOUND_CODE);
            return new ResponseEntity<>(apiError, headers, status);
        }

        if (exception instanceof DuplicateEntityException) {
            HttpStatus status = HttpStatus.FORBIDDEN;
            ApiError apiError = new ApiError(exception.getMessage(), HttpStatus.CONFLICT.value() * 100);
            return new ResponseEntity<>(apiError, headers, status);
        }

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(exception, null, headers, status, request);
    }

    protected ResponseEntity<ApiError> handleExceptionInternal(Exception ex, ApiError body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        return new ResponseEntity<>(body, headers, status);
    }
}
