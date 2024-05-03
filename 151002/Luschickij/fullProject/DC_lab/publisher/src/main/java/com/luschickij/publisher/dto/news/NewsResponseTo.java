package com.luschickij.publisher.dto.news;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.luschickij.publisher.dto.label.LabelResponseTo;
import com.luschickij.publisher.dto.creator.CreatorResponseTo;
import com.luschickij.publisher.model.IdentifiedEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@JsonRootName("news")
@AllArgsConstructor
@NoArgsConstructor
public class NewsResponseTo extends IdentifiedEntity {

    private Long id;

    private Long creatorId;
    private String title;
    private String content;
    private Date created;
    private Date modified;

    private List<Long> labels;
}
