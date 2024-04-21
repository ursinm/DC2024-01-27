package by.bsuir.publisherservice.client.discussion.message;

import by.bsuir.publisherservice.client.discussion.request.DiscussionMessageRequestTo;
import by.bsuir.publisherservice.client.discussion.response.DiscussionMessageResponseTo;
import by.bsuir.publisherservice.dto.message.TopicMessage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class UpdateMessage extends TopicMessage {
    private final DiscussionMessageRequestTo message;

    private final DiscussionMessageResponseTo result;

    public UpdateMessage(DiscussionMessageRequestTo message, DiscussionMessageResponseTo result) {
        super(Operation.UPDATE, null);
        this.message = message;
        this.result = result;
    }
}
