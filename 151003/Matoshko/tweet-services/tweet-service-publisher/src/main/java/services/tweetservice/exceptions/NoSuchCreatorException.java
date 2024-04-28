package services.tweetservice.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NoSuchCreatorException extends IllegalArgumentException {
    private final Long creatorId;

    public NoSuchCreatorException(Long creatorId) {
        super(String.format("Creator with id %d is not found in DB", creatorId));
        this.creatorId = creatorId;
    }
}
