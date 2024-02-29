package services.tweetservice.domain.request;

public record StickerRequestTo(
        Long Id,
        String name,
        Long tweetId) {
}
