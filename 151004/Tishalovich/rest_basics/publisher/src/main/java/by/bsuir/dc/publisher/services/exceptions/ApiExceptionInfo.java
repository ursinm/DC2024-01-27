package by.bsuir.dc.publisher.services.exceptions;

import lombok.*;

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
