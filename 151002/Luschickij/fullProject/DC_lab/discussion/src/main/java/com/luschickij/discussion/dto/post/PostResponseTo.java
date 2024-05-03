package com.luschickij.discussion.dto.post;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.luschickij.discussion.model.IdentifiedEntity;
import lombok.*;

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
    private String country;
}
