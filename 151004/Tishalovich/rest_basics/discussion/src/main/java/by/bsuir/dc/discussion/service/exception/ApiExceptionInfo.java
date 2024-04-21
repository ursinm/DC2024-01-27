package by.bsuir.dc.discussion.service.exception;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ApiExceptionInfo {

    private String errorMessage;

    private int statusCode;

    public ApiExceptionInfo(int httpCode, int subCode, String errorMessage) {
        statusCode = httpCode*100 + subCode;
        this.errorMessage = errorMessage;
    }

}
