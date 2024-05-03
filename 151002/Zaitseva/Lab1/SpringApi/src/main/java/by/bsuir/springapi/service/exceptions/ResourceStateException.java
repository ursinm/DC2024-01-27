package by.bsuir.springapi.service.exceptions;

public class ResourceStateException extends ResourceException {
    public ResourceStateException(String message, int code) {
        super(message, code);
    }
}
