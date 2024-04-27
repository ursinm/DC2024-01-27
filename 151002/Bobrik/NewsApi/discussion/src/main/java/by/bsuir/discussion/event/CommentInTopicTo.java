package by.bsuir.discussion.event;

import by.bsuir.discussion.model.entity.CommentState;

import java.util.Locale;

public record CommentInTopicTo(
        Long id,
        Long newsId,
        Locale country,
        String content,
        CommentState state
) {
}