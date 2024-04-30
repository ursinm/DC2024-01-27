package services.tweetservice.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ServiceException extends Exception{
    private final ErrorResponseTo errorDto;

    public ServiceException(ErrorResponseTo errorDto) {
        this.errorDto = errorDto;
    }
}
