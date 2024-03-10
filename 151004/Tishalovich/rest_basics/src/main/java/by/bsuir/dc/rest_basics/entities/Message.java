package by.bsuir.dc.rest_basics.entities;

import by.bsuir.dc.rest_basics.entities.common.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Message extends AbstractEntity {

    private Long storyId;

    private String content;

    public Message(Long id, Long storyId, String content) {
        setId(id);
        this.storyId = storyId;
        this.content = content;
    }

}
