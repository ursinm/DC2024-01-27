package by.bsuir.dc.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class Tweet extends AbstractEntity{
    private Long creatorId;
    private String title;
    private String content;
    private Instant created;
    private Instant modified;
}
