package com.luschickij.discussion.model;


import edu.umd.cs.findbugs.annotations.DefaultAnnotation;
import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;


@Table(value = "tbl_post")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Post extends IdentifiedEntity {


    @PrimaryKeyColumn(name = "country",
            type = PrimaryKeyType.PARTITIONED)
    private String country;

    @PrimaryKeyColumn(name = "newsid",
            ordinal = 0,
            type = PrimaryKeyType.CLUSTERED)
    private Long newsId;

    @PrimaryKeyColumn(
            name = "id",
            ordinal = 1,
            type = PrimaryKeyType.CLUSTERED)
    private Long id;

    private String content;
}
