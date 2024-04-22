package by.bsuir.publisher.event;

import by.bsuir.publisher.model.entity.CommentState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentInTopicTo {
    private Long id;
    private Long newsId;
    private Locale country;
    private String content;
    private CommentState state;
}