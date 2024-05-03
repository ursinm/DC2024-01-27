package by.bsuir.discussion.model.response;

public record CommentResponseTo(
        Long id,
        Long storyId,
        String content
) {
}
