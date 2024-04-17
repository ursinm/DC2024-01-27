package by.bsuir.publisher.service.exceptions;

public class CommentExchangeTimeoutException extends RuntimeException {
    public CommentExchangeTimeoutException() {
        super();
    }

    public CommentExchangeTimeoutException(String message) {
        super(message);
    }

    public CommentExchangeTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommentExchangeTimeoutException(Throwable cause) {
        super(cause);
    }

    protected CommentExchangeTimeoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
