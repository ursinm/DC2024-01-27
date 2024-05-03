package by.denisova.rest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Comment extends AbstractEntity {

    private Long storyId;
    private String content;
}
