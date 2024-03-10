package by.bsuir.newsapi.service.exceptions;

public class ResourceStateException extends ResourceException {
    public ResourceStateException(String message, int code) {
        super(message, code);
    }
}
