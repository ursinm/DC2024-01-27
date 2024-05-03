package by.haritonenko.rest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Note extends AbstractEntity {

    private Long storyId;
    private String content;
}
