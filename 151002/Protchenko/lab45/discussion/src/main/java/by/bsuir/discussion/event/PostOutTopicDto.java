package by.bsuir.discussion.event;

public record PostOutTopicDto(
        Long id,
        Long issueId,
        String content
) {
}
