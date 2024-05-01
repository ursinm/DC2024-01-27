package by.bsuir.discussion.model.entity;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.util.Locale;

@Data
@PrimaryKeyClass
public class PostKey {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.TEXT)
    private Locale country;

    @PrimaryKeyColumn(
            name = "issue_id",
            type = PrimaryKeyType.CLUSTERED,
            ordinal = 0
    )
    private Long issueId;

    @PrimaryKeyColumn(
            type = PrimaryKeyType.CLUSTERED,
            ordinal = 1
    )
    private Long id;
}
