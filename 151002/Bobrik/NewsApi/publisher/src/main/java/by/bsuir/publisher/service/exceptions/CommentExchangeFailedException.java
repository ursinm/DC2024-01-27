package by.bsuir.publisher.service.exceptions;

public class CommentExchangeFailedException extends RuntimeException {
    public CommentExchangeFailedException() {
        super();
    }

    public CommentExchangeFailedException(String message) {
        super(message);
    }

    public CommentExchangeFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommentExchangeFailedException(Throwable cause) {
        super(cause);
    }
}