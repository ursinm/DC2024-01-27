package com.example.discussion.event;

public record MessageOutTopicTo(
        Long id,
        Long issueId,
        String content
) {
}
