package com.example.publisher.model.response;

public record ErrorResponseTo(
        int code,
        String message,
        String[] errMessages
) {
}
