package by.bsuir.discussion.event;

public record CommentOutTopicTo(
        Long id,
        Long newsId,
        String content
) {
}