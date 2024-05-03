package by.bsuir.publisher.event;

public record CommentOutTopicTo(
        Long id,
        Long newsId,
        String content
) {
}