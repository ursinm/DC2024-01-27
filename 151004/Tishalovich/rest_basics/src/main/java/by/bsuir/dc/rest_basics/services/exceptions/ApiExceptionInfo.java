package by.bsuir.dc.rest_basics.services.exceptions;

import lombok.Getter;

@Getter
public class ApiExceptionInfo {

    private final int statusCode;

    private final String errorMessage;

    public ApiExceptionInfo(int httpStatusCode, int subStatusCode, String errorMessage) {
        statusCode = httpStatusCode*100 + subStatusCode;
        this.errorMessage = errorMessage;
    }

    public int getHttpStatusCode() {
        return statusCode/100;
    }

}
