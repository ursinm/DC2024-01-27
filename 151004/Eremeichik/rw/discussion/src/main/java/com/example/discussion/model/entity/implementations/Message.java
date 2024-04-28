package com.example.discussion.model.entity.implementations;

import com.example.discussion.model.entity.interfaces.EntityModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Getter
@Setter
@Table("tbl_message")
public class Message implements EntityModel<Long> {

    @PrimaryKey
    private Long id;
    private String content;
    private Long newsId;
    @Transient
    private News news;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id){
        this.id = id;
    }
}
