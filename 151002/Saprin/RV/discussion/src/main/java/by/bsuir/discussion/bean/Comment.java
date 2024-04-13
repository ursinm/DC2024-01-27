package by.bsuir.discussion.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigInteger;

@Table("tbl_comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @PrimaryKeyColumn(name= "com_id", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    private BigInteger com_id;

    @PrimaryKeyColumn(name = "com_country", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String com_country;

    @PrimaryKeyColumn(name = "com_issueid", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private BigInteger com_issueId;

    @Column("com_content")
    private String com_content;
}
