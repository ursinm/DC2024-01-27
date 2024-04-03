package by.bsuir.publisher.exception;

public class NoteExchangeTimeoutException extends RuntimeException {
    public NoteExchangeTimeoutException() {
        super();
    }

    public NoteExchangeTimeoutException(String message) {
        super(message);
    }

    public NoteExchangeTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoteExchangeTimeoutException(Throwable cause) {
        super(cause);
    }
}
