package by.bsuir.publisher.service.exceptions;

public class MessageExchangeFailedException extends RuntimeException {
    public MessageExchangeFailedException() {
        super();
    }

    public MessageExchangeFailedException(String message) {
        super(message);
    }

    public MessageExchangeFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageExchangeFailedException(Throwable cause) {
        super(cause);
    }
}
