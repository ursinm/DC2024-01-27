package by.bsuir.poit.dc.rest.api.exceptions;

/**
 * @author Paval Shlyk
 * @since 31/01/2024
 */
public class ResourceModifyingException extends RuntimeException {
    public ResourceModifyingException(String message) {
	super(message);
    }

    public ResourceModifyingException(Throwable t) {
	super(t);
    }

}
