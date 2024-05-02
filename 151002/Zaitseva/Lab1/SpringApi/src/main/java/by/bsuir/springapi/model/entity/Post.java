package by.bsuir.springapi.model.entity;

import by.bsuir.springapi.model.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Post extends AbstractEntity {
    private Long issueId;
    
    private String content;
}
