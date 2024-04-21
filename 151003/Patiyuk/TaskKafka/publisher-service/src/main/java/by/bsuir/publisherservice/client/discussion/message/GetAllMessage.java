package by.bsuir.publisherservice.client.discussion.message;

import by.bsuir.publisherservice.client.discussion.response.DiscussionMessageResponseTo;
import by.bsuir.publisherservice.dto.message.TopicMessage;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public final class GetAllMessage extends TopicMessage {
    private final Integer page;
    private final Integer size;

    private final List<DiscussionMessageResponseTo> result;

    public GetAllMessage(Integer page, Integer size, List<DiscussionMessageResponseTo> result) {
        super(Operation.GET_ALL, null);
        this.page = page;
        this.size = size;
        this.result = result;
    }
}
