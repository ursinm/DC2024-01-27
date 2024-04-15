package by.bsuir.dc.discussion.entity;

public record MessageRequestTo(
        String country,
        Long storyId,
        Long id,
        String content
) {
}
