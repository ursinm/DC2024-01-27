package by.bsuir.dc.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Note extends AbstractEntity {
    private Long tweetId;
    private String content;
}
