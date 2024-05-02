package com.example.discussion.model.response;

public record MessageResponseTo(
        Long id,
        Long issueId,
        String content
) {
}
