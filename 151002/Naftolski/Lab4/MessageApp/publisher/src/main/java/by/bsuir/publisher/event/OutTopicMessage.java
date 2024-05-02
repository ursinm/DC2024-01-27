package by.bsuir.publisher.event;

import java.util.List;

public record OutTopicMessage(
        List<MessageOutTopicTo> resultList
) {
}
