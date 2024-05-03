package com.luschickij.publisher.dto.post;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.luschickij.publisher.dto.news.NewsResponseTo;
import com.luschickij.publisher.model.IdentifiedEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@JsonRootName("post")
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseTo extends IdentifiedEntity {

    private Long id;
    private Long newsId;
    private String content;
}
