package services.tweetservice.domain.request;

public record MessageRequestTo(
        Long id,
        Long tweetId,
        String content) {
}
