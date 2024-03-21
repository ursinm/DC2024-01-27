package app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tweet {
    private Long id;
    private String title;
    private String content;
    private Timestamp created;
    private Timestamp modified;
    private Long authorId;
    private Long markerId;
}
