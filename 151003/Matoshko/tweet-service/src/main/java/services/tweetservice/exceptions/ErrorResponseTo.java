package services.tweetservice.exceptions;

public record ErrorResponseTo(
        String errorMessage,
        String errorCode) {
}
