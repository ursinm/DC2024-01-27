package com.example.discussion.model.entity;
import lombok.*;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("tbl_message")
public class Message {
    @PrimaryKeyColumn(name = "message_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private BigInteger id;

    @PrimaryKeyColumn(name = "message_issueid", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    private BigInteger issueId;

    @PrimaryKeyColumn(name = "message_country", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String message_country;

    @Column("message_content")
    private String content;
}