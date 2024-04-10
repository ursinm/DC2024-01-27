package by.bsuir.discussion.event;

import by.bsuir.discussion.model.NoteState;

import java.util.Locale;

public record NoteInTopicDto(
        Long id,
        Long tweetId,
        Locale country,
        String content,
        NoteState state
) {
}
