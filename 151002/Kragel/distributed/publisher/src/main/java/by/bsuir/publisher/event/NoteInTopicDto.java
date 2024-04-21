package by.bsuir.publisher.event;


import by.bsuir.publisher.model.NoteState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteInTopicDto {
    private Long id;
    private Long tweetId;
    private Locale country;
    private String content;
    private NoteState state;
}
