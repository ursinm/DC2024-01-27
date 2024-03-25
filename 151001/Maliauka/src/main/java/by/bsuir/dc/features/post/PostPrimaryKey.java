package by.bsuir.dc.features.post;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.util.UUID;

@PrimaryKeyClass
public class PostPrimaryKey {
    @PrimaryKeyColumn(
            name = "news_id",
            ordinal = 0,
            ordering = Ordering.DESCENDING,
            type = PrimaryKeyType.PARTITIONED
    )
    private UUID newsId;

    @PrimaryKeyColumn(
            name = "id",
            ordinal = 1,
            ordering = Ordering.DESCENDING,
            type = PrimaryKeyType.CLUSTERED
    )
    private UUID id;
}
