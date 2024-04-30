package service.tweetservicediscussion.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NoSuchMessageException extends IllegalArgumentException {
    private final Long messageId;

    public NoSuchMessageException(Long messageId) {
        super(String.format("Message with id %d is not found in DB", messageId));
        this.messageId = messageId;
    }
}
