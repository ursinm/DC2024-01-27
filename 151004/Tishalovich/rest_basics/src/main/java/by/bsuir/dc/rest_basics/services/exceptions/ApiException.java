package by.bsuir.dc.rest_basics.services.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiException extends Exception {

    private ApiExceptionInfo apiExceptionInfo;

}
