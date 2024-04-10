package services.tweetservice.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NoSuchTweetException extends IllegalArgumentException {
    private final Long tweetId;

    public NoSuchTweetException(Long tweetId) {
        super(String.format("Tweet with id %d is not found in DB", tweetId));
        this.tweetId = tweetId;
    }
}
