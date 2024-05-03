package com.luschickij.discussion.dto.post;

import com.luschickij.discussion.model.IdentifiedEntity;
import jakarta.validation.constraints.Size;
import lombok.*;

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
