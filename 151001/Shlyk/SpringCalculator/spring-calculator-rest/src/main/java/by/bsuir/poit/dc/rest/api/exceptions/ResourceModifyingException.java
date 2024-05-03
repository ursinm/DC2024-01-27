package by.bsuir.poit.dc.rest.api.exceptions;

/**
 * @author Paval Shlyk
 * @since 31/01/2024
 */
public class ResourceModifyingException extends ResourceException {

    public ResourceModifyingException(String message, int code) {
	super(message, code);
    }

}
