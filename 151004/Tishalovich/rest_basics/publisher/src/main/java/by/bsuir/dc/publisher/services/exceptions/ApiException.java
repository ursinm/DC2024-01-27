package by.bsuir.dc.publisher.services.exceptions;

import com.google.common.base.Objects;
import lombok.Getter;

import java.io.IOException;

@Getter
public class ApiException extends IOException {

    private final int statusCode;

    private final String errorMessage;

    public ApiException(int httpStatusCode, int subStatusCode, String errorMessage) {
        statusCode = httpStatusCode*100 + subStatusCode;
        this.errorMessage = errorMessage;
    }

    public int getHttpStatusCode() {
        return statusCode/100;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiException that = (ApiException) o;
        return statusCode == that.statusCode && errorMessage.equals(that.errorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(statusCode, errorMessage);
    }

}
