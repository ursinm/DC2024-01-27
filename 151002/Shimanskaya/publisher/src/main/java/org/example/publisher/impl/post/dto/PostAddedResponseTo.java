package org.example.publisher.impl.post.dto;

import java.math.BigInteger;

public record PostAddedResponseTo(
        BigInteger id,
        BigInteger issueId,
        String content,
        String status
) {
}