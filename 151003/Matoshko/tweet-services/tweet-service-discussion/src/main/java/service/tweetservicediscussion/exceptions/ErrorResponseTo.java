package service.tweetservicediscussion.exceptions;

import lombok.Builder;

@Builder
public record ErrorResponseTo(
        String errorMessage,
        String errorCode) {
}
