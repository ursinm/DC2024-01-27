package com.example.discussion.model.response;

public record ErrorResponseTo(
        int code,
        String message,
        String[] errMessages
) {
}
