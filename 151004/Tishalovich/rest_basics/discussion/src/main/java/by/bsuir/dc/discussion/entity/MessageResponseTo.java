package by.bsuir.dc.discussion.entity;

public record MessageResponseTo(
        String country,
        Long storyId,
        Long id,
        String content
) {
}
