package by.bsuir.publisher.util;

import by.bsuir.publisher.dto.response.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorUtil {

    private ErrorUtil() { }

    public static Integer errorCode(HttpStatus status, Integer subCode){
        return status.value() * 100 + subCode;
    }

    public static ResponseEntity<ErrorResponseDto> errorResponseEntity(HttpStatus status,
                                                                       Integer subCode,
                                                                       String errorMessage) {
        return ResponseEntity
                .status(status)
                .body(new ErrorResponseDto(errorCode(status, subCode), errorMessage));
    }
}
