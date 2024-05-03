package by.bsuir.discussion.event;

import by.bsuir.discussion.model.entity.PostState;

import java.util.Locale;

public record PostInTopicDto(
        Long id,
        Long issueId,
        Locale country,
        String content,
        PostState state
) {
}
