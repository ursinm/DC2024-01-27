package by.bsuir.discussion.model.entity;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Table("tbl_post")
public class Post {

    @PrimaryKey
    private PostKey key;

    @Column("content")
    @CassandraType(type = CassandraType.Name.VARCHAR)
    private String content;

    @Column("state")
    @CassandraType(type = CassandraType.Name.VARCHAR)
    private PostState state;

}
