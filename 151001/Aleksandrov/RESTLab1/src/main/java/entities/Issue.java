package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Issue {
    Long id;
    Long editorId;
    String title;
    String content;
    Timestamp created;
    Timestamp modified;
}
