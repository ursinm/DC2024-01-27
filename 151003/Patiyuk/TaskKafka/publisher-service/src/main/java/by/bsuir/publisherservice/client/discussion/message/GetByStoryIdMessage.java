package by.bsuir.publisherservice.client.discussion.message;

import by.bsuir.publisherservice.client.discussion.response.DiscussionMessageResponseTo;
import by.bsuir.publisherservice.dto.message.TopicMessage;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public final class GetByStoryIdMessage extends TopicMessage {
    private final Long storyId;
    private final Integer page;
    private final Integer size;

    private final List<DiscussionMessageResponseTo> result;

    public GetByStoryIdMessage(Long storyId, Integer page, Integer size, List<DiscussionMessageResponseTo> result) {
        super(Operation.GET_BY_STORY_ID, null);
        this.storyId = storyId;
        this.page = page;
        this.size = size;
        this.result = result;
    }
}
