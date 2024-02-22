package by.bsuir.dc.rest_basics.entities;

import by.bsuir.dc.rest_basics.entities.common.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Message extends AbstractEntity {

    private Long storyId;

    private String content;

}
