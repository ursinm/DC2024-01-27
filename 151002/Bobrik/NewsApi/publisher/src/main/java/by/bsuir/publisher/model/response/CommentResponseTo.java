package by.bsuir.publisher.model.response;

public record CommentResponseTo(
        Long id,
        Long newsId,
        String content
) {
}
