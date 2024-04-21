package by.bsuir.publisherservice.client.discussion.message;

import by.bsuir.publisherservice.client.discussion.response.DiscussionMessageResponseTo;
import by.bsuir.publisherservice.dto.message.TopicMessage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class GetByIdMessage extends TopicMessage {
    private final Long id;

    private final DiscussionMessageResponseTo result;

    public GetByIdMessage(Long id, DiscussionMessageResponseTo result) {
        super(Operation.GET_BY_ID, null);
        this.id = id;
        this.result = result;
    }
}
