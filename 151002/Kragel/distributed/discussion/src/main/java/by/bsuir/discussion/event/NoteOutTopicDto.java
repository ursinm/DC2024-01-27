package by.bsuir.discussion.event;

public record NoteOutTopicDto(
        Long id,
        Long tweetId,
        String content
) {
}
