package com.example.publisher.event;

import java.util.UUID;

public record InTopicEvent(
        UUID id,
        InTopicMessage message
)  implements Exchangeable {
}
