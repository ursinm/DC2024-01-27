package by.bsuir.dc.rest_basics.controllers;

import by.bsuir.dc.rest_basics.services.exceptions.ApiException;
import by.bsuir.dc.rest_basics.services.exceptions.ApiExceptionInfo;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler( ApiException.class )
    public ResponseEntity<ApiExceptionInfo> handleApiException(ApiException e) {
        return new ResponseEntity<>(
                new ApiExceptionInfo(e.getErrorMessage(), e.getStatusCode()),
                HttpStatusCode.valueOf(e.getHttpStatusCode())
        );
    }

}
