package by.bsuir.discussion.model.response;

public record CommentResponseTo(
        Long id,
        Long newsId,
        String content
) {
}
