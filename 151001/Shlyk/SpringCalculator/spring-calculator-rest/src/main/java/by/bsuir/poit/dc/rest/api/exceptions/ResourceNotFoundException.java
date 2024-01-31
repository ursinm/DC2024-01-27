package by.bsuir.poit.dc.rest.api.exceptions;

/**
 * @author Paval Shlyk
 * @since 31/01/2024
 */

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
	super(message);
    }

    public ResourceNotFoundException(Throwable t) {
	super(t);
    }
}
