package com.example.publisher.event;

public record MessageOutTopicTo(
        Long id,
        Long issueId,
        String content
) {
}
