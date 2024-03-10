package by.bsuir.newsapi.model.response;

public record CommentResponseTo(
        Long id,
        Long newsId,
        String content
) {
}
