package application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoryResponseTo implements Serializable {
    private Long id;
    private String title;
    private String content;
    private Timestamp created;
    private Timestamp modified;
    private Long authorId;
    private Long markerId;
}
