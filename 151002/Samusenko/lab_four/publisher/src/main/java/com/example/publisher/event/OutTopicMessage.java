package com.example.publisher.event;

import java.util.List;

public record OutTopicMessage(
        List<MessageOutTopicTo> resultList
) {
}
