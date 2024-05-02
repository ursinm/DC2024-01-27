package by.bsuir.publisher.event;

import by.bsuir.publisher.model.entity.MessageState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageInTopicTo {
    private Long id;
    private Long storyId;
    private Locale country;
    private String content;
    private MessageState state;
}
