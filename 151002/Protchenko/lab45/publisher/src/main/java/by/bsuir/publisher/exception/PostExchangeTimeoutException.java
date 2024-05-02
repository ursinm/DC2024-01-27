package by.bsuir.publisher.exception;

import java.util.concurrent.TimeoutException;

public class PostExchangeTimeoutException extends RuntimeException {

    public PostExchangeTimeoutException(String string, TimeoutException ex) {
    }
}
