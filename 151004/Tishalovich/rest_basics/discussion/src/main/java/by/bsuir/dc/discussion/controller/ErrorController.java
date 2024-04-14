package by.bsuir.dc.discussion.controller;

import by.bsuir.dc.discussion.service.exception.ApiException;
import by.bsuir.dc.discussion.service.exception.ApiExceptionInfo;
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
