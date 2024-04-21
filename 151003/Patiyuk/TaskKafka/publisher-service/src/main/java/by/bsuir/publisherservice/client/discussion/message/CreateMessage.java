package by.bsuir.publisherservice.client.discussion.message;

import by.bsuir.publisherservice.client.discussion.request.DiscussionMessageRequestTo;
import by.bsuir.publisherservice.client.discussion.response.DiscussionMessageResponseTo;
import by.bsuir.publisherservice.dto.message.TopicMessage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class CreateMessage extends TopicMessage {
    private final DiscussionMessageRequestTo message;

    private final DiscussionMessageResponseTo result;

    public CreateMessage(DiscussionMessageRequestTo message, DiscussionMessageResponseTo result) {
        super(Operation.CREATE, null);
        this.message = message;
        this.result = result;
    }
}
