package by.bsuir.discussion.event;

import by.bsuir.discussion.model.entity.MessageState;

import java.util.Locale;

public record MessageInTopicTo(
    Long id,
    Long storyId,
    Locale country,
    String content,
    MessageState state
){

}
