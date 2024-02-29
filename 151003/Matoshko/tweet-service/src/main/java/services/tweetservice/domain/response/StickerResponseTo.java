package services.tweetservice.domain.response;

import java.util.List;

public record StickerResponseTo(
        Long id,
        String name,
        List<TweetResponseTo> tweetList) {
}
