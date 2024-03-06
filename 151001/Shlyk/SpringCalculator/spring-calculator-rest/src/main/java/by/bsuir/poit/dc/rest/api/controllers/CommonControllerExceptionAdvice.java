package by.bsuir.poit.dc.rest.api.controllers;

import by.bsuir.poit.dc.rest.api.dto.request.ErrorDto;
import by.bsuir.poit.dc.rest.api.exceptions.ResourceBusyException;
import by.bsuir.poit.dc.rest.api.exceptions.ResourceModifyingException;
import by.bsuir.poit.dc.rest.api.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Paval Shlyk
 * @since 01/02/2024
 */
@RestControllerAdvice
public class CommonControllerExceptionAdvice {
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ErrorDto> catchNotFoundException(ResourceNotFoundException exception) {
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getError());
    }

    @ExceptionHandler({ResourceModifyingException.class})
    public ResponseEntity<ErrorDto> catchModifyingException(ResourceModifyingException e) {
	return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getError());
    }

    @ExceptionHandler({ResourceBusyException.class})
    public ResponseEntity<ErrorDto> catchBusyException(ResourceBusyException e) {
	return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getError());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorDto> catchValidationException(MethodArgumentNotValidException e) {
	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.errorOf(e));
    }

    @ExceptionHandler({Throwable.class})
    public ResponseEntity<ErrorDto> catchThrowable(Throwable t) {
	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorOf(t));
    }

    private ErrorDto errorOf(Throwable t) {
	return ErrorDto.builder()
		   .errorMessage("Server doesn't know how to deal your request. Sorry...(")
		   .errors(new String[]{t.getMessage()})
		   .build();
    }

    private ErrorDto errorOf(MethodArgumentNotValidException e) {
	List<String> messages = new ArrayList<>();
	for (ObjectError error : e.getAllErrors()) {
	    String message;
	    if (error instanceof FieldError fieldError) {
		message = STR."Field `\{fieldError.getField()}`: \{fieldError.getDefaultMessage()}";
	    } else {
		message = error.toString();
	    }
	    messages.add(message);
	}
	String errorMessage = "Input request violates some rules";
	return ErrorDto.builder()
		   .errorCode(77)//validation error
		   .errorMessage(errorMessage)
		   .errors(messages.toArray(String[]::new))
		   .build();
    }
}
