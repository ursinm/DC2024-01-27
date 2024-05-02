package by.bsuir.publisher.service.exceptions;

public class MessageExchangeTimeoutException extends RuntimeException {
    public MessageExchangeTimeoutException() {
        super();
    }

    public MessageExchangeTimeoutException(String message) {
        super(message);
    }

    public MessageExchangeTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageExchangeTimeoutException(Throwable cause) {
        super(cause);
    }

    protected MessageExchangeTimeoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
