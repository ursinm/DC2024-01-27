package by.bsuir.dc.rest_basics.services.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ApiException extends Exception {

    private final int statusCode;

    private final String errorMessage;

    public ApiException(int httpStatusCode, int subStatusCode, String errorMessage) {
        statusCode = httpStatusCode*100 + subStatusCode;
        this.errorMessage = errorMessage;
    }

    public int getHttpStatusCode() {
        return statusCode/100;
    }

}
