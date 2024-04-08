package com.example.rv.impl.note;

public record NoteResponseTo(
        Long id,
        Long newsId,
        String content
) {
}
