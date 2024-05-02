package by.bsuir.springapi.model.entity;

import by.bsuir.springapi.model.AbstractEntity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@SuperBuilder
public class Issue extends AbstractEntity {
    private Long creatorId;
    
    private String title;
    
    private String i_content;
    
    private LocalDateTime created;
    
    private LocalDateTime modified;
}
