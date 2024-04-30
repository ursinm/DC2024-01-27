package service.tweetservicediscussion.domain.entity;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Table(value = "tbl_message")
public class Message {
    @PrimaryKeyColumn(name = "id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private Long id;
    @PrimaryKeyColumn(name = "tweet_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private Long tweetId;
    @Column(value = "content")
    private String content;
}
