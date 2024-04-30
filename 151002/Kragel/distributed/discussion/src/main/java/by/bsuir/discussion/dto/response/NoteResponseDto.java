package by.bsuir.discussion.dto.response;

public record NoteResponseDto(
        Long id,
        Long tweetId,
        String content
) {
}
