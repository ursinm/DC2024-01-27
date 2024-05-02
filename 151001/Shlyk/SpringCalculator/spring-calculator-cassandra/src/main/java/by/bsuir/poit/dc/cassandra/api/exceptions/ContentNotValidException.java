package by.bsuir.poit.dc.cassandra.api.exceptions;

/**
 * @author Paval Shlyk
 * @since 08/04/2024
 */
public class ContentNotValidException extends ResourceException{
    public ContentNotValidException(String message, int code) {
	super(message, code);
    }
}
