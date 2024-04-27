package by.bsuir.dc.discussion.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@AllArgsConstructor
@Table
public class Message {
    private String country;

    private Long storyId;

    @PrimaryKey
    private Long id;

    private String content;
}
