package by.rusakovich.newsdistributedsystem.controller;

import by.rusakovich.newsdistributedsystem.error.exception.BaseDSException;
import by.rusakovich.newsdistributedsystem.error.response.ErrorResponseTO;
import by.rusakovich.newsdistributedsystem.model.dto.mapper.ConversionError;
import by.rusakovich.newsdistributedsystem.service.exception.CantCreate;
import by.rusakovich.newsdistributedsystem.service.exception.NotFound;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFound.class)
    public ResponseEntity<ErrorResponseTO> handleNotFound(NotFound e) {
        return makeErrorResponseFromException(HttpStatus.NOT_FOUND, e);
    }

    @ExceptionHandler(CantCreate.class)
    public ResponseEntity<ErrorResponseTO> handleCantCreate(CantCreate e) {
        return makeErrorResponseFromException(HttpStatus.CONFLICT, e);
    }

    @ExceptionHandler(ConversionError.class)
    public ResponseEntity<ErrorResponseTO> handleConversionError(ConversionError e) {
        return makeErrorResponseFromException(HttpStatus.INTERNAL_SERVER_ERROR, e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseTO> catchValidationException(MethodArgumentNotValidException e) {
        return makeErrorResponseFromException(HttpStatus.BAD_REQUEST, e);
    }

    private ResponseEntity<ErrorResponseTO> makeErrorResponseFromException(HttpStatus status, BaseDSException exception){
        ErrorResponseTO error = new ErrorResponseTO(Integer.toString(status.value() * 100 + exception.getErrorCode()), exception.getErrorMessage());
        return ResponseEntity.status(status).body(error);
    }
    private ResponseEntity<ErrorResponseTO> makeErrorResponseFromException(HttpStatus status, MethodArgumentNotValidException exception){
        ErrorResponseTO error = new ErrorResponseTO(Integer.toString(status.value() * 100), exception.getMessage());
        return ResponseEntity.status(status).body(error);
    }

    private ResponseEntity<ErrorResponseTO> makeErrorResponseFromException(HttpStatus status, Throwable exception){
        ErrorResponseTO error = new ErrorResponseTO(Integer.toString(status.value() * 100), exception.toString());
        return ResponseEntity.status(status).body(error);
    }
}
