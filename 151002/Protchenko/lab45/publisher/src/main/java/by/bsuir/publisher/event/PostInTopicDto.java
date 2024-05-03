package by.bsuir.publisher.event;

import by.bsuir.publisher.model.entity.PostState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostInTopicDto{
        Long id;
        Long issueId;
        Locale country;
        String content;
        PostState state;
}
