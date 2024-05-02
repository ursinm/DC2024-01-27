package org.example.publisher.impl.comment.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class CommentRequestTo {
    private BigInteger id;
    private BigInteger storyId;
    @Size(min = 2, max = 2048)
    String content;
}
