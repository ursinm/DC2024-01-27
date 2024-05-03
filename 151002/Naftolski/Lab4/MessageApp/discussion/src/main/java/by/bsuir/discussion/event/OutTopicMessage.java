package by.bsuir.discussion.event;

import java.util.List;

public record OutTopicMessage(
    List<MessageOutTopicTo> resultList
) {
}
