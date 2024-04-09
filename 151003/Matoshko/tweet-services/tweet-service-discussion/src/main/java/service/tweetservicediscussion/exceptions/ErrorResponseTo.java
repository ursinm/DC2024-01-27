package service.tweetservicediscussion.exceptions;

public record ErrorResponseTo(
        String errorMessage,
        String errorCode) {
}
