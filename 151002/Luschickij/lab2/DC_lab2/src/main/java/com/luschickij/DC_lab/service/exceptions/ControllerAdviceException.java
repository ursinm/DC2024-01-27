package com.luschickij.DC_lab.service.exceptions;

import com.luschickij.DC_lab.model.response.ErrorResponseTo;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerAdviceException {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseTo> catchNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getErrorResponseTo());
    }

    @ExceptionHandler(ResourceStateException.class)
    public ResponseEntity<ErrorResponseTo> catchStateException(ResourceStateException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getErrorResponseTo());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseTo> catchValidationException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(takeError(e));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponseTo> catchThrowable(Throwable e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(takeError(e));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseTo> catchDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(takeError(e));
    }

    private static ErrorResponseTo takeError(MethodArgumentNotValidException e) {
        List<String> posts = new ArrayList<>();

        for (ObjectError objectError : e.getAllErrors()) {
            if (objectError instanceof FieldError fieldError) {
                posts.add("FieldError " + fieldError.getField() + ": " + fieldError.getDefaultMessage());
            } else {
                posts.add(objectError.toString());
            }
        }

        return new ErrorResponseTo(
                HttpStatus.BAD_REQUEST.value() * 100 + 5,
                "Some parameters are not correct",
                posts.toArray(String[]::new)
        );
    }

    private static ErrorResponseTo takeError(DataIntegrityViolationException e) {
        return new ErrorResponseTo(HttpStatus.FORBIDDEN.value() * 100 + 6, "Request data is not correct", new String[] {e.getMessage()});
    }

    private static ErrorResponseTo takeError(Throwable e) {
        return new ErrorResponseTo(
                HttpStatus.BAD_REQUEST.value() * 100 + 6,
                "Special error caught in the request",
                new String[] {e.getMessage()}
        );
    }
}
