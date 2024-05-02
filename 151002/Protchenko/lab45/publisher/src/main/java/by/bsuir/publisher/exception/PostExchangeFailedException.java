package by.bsuir.publisher.exception;

public class PostExchangeFailedException extends RuntimeException {

    public PostExchangeFailedException(String string) {
    }

    public PostExchangeFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
