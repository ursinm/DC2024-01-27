package com.example.distributedcomputing.model.response;

public record StoryResponseTo (
        Long id,
        String title,
        String content,
        Long editorId
) {
}
