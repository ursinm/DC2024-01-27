package com.example.discussion.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Getter
@Setter
@Table("tbl_message")
@Data
@AllArgsConstructor
public class Message {
    @PrimaryKey
    private MessageKey key;
    @Column("message_content")
    private String content;
    @Column("message_state")
    @CassandraType(type = CassandraType.Name.VARCHAR)
    private MessageState state;
}
