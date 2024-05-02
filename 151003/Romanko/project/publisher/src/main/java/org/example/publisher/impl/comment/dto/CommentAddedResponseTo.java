package org.example.publisher.impl.comment.dto;

import java.math.BigInteger;

public record CommentAddedResponseTo(
        BigInteger id,
        BigInteger storyId,
        String content,
        String status
) {
}