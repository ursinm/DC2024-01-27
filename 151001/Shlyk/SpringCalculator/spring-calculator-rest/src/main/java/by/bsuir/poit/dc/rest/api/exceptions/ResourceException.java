package by.bsuir.poit.dc.rest.api.exceptions;

import by.bsuir.poit.dc.rest.api.dto.request.ErrorDto;
import jakarta.annotation.Nullable;
import lombok.Getter;

/**
 * The wrapper around exception to make exception handler more lightweight.
 * This exception is used with violation of business logic rules.
 *
 * @author Paval Shlyk
 * @since 01/02/2024
 */
@Getter
public class ResourceException extends RuntimeException {
    @Nullable
    private final ErrorDto error;

    public ResourceException(String message, int code) {
	super(message);
	error = ErrorDto.builder()
		    .errorCode(code)
		    .errorMessage(message)
		    .build();
    }

    @Override

    public synchronized Throwable fillInStackTrace() {
	return this;
    }
}
