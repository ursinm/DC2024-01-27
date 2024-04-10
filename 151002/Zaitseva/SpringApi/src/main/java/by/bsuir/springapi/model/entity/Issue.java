package by.bsuir.springapi.model.entity;

import by.bsuir.springapi.model.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
public class Issue extends AbstractEntity {
    private Long creatorId;
    
    private String title;
    
    private String content;
    
    private LocalDateTime created;
    
    private LocalDateTime modified;
}
