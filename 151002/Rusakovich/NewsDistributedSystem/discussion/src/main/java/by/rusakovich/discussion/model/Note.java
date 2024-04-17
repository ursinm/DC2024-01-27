package by.rusakovich.discussion.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@Getter
@Setter
@Entity
@Table(name = "tbl_note")
//@IdClass(NoteId.class)
public class Note {
    @PrimaryKeyColumn(type= PrimaryKeyType.PARTITIONED)
    @Id
    private String country;
    @PrimaryKeyColumn(name = "news_id", ordinal = 0, type = PrimaryKeyType.CLUSTERED)
    private Long newsId;
    @PrimaryKeyColumn(ordinal = 1, type= PrimaryKeyType.CLUSTERED)
    private Long id;
    private String content;
}
