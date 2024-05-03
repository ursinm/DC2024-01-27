package discussion.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.*;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "tbl_note")
public class Note {
    @PrimaryKeyColumn(name = "country", ordinal = 0,type = PrimaryKeyType.PARTITIONED)
    private String country;
    @PrimaryKeyColumn(name = "story_id", ordinal = 0, type = PrimaryKeyType.CLUSTERED)
    private Long storyId;
    @PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.CLUSTERED)
    private Long id;
    private String content;
}