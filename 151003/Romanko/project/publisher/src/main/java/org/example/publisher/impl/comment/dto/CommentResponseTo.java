package org.example.publisher.impl.comment.dto;

import java.math.BigInteger;

public record CommentResponseTo(
        BigInteger id,
        BigInteger storyId,
        String content
) {
}
