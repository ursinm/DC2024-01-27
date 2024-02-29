package services.tweetservice.domain.request;

public record TweetRequestTo(
        Long id,
        Long creatorId,
        String title,
        String content) {
}
