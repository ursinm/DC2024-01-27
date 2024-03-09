package by.bsuir.newsapi.model.entity;

import by.bsuir.newsapi.model.AbstractEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Comment extends AbstractEntity {
    private Long newsId;
    
    private String content;
}
