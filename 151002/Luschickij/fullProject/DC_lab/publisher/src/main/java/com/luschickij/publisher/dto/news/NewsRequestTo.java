package com.luschickij.publisher.dto.news;

import com.luschickij.publisher.model.IdentifiedEntity;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NewsRequestTo extends IdentifiedEntity {

    private Long id;

    private Long creatorId;
    @Size(min = 2, max = 64)
    private String title;
    @Size(min = 4, max = 2048)
    private String content;
    private Date created;
    private Date modified;

    private List<Long> labels;
}
