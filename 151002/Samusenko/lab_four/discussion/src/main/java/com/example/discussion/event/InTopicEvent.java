package com.example.discussion.event;

import java.util.UUID;

public record InTopicEvent(
        UUID id,
        InTopicMessage message
)  implements Exchangeable {
}
