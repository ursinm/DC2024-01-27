package org.example.discussion.impl.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("tbl_comment")
public class Comment {
    @PrimaryKeyColumn(name = "comment_id", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    private BigInteger id;

    @PrimaryKeyColumn(name = "comment_storyid", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private BigInteger storyId;

    @PrimaryKeyColumn(name = "comment_country", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String com_country;

    @Column("comment_content")
    private String content;
}
