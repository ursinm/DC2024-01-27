package com.luschickij.publisher.dto.post;

import com.luschickij.publisher.dto.news.NewsResponseTo;
import com.luschickij.publisher.model.IdentifiedEntity;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestTo extends IdentifiedEntity {

    private Long id;
    private Long newsId;
    @Size(min = 2, max = 2048)
    private String content;

    private String country;
}
