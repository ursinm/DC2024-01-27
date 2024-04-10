package by.bsuir.publisher.event;

public record NoteOutTopicDto(
        Long id,
        Long tweetId,
        String content
) {
}
