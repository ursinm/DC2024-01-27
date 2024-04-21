package by.bsuir.publisherservice.client.discussion.message;

import by.bsuir.publisherservice.dto.message.TopicMessage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class DeleteMessage extends TopicMessage {
    private final Long id;

    public DeleteMessage(Long id) {
        super(Operation.DELETE, null);
        this.id = id;
    }
}
