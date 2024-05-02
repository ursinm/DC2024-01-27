package com.example.discussion.event;

import java.util.UUID;

public record OutTopicEvent(
        UUID id,
        OutTopicMessage message
) implements Exchangeable {
}
