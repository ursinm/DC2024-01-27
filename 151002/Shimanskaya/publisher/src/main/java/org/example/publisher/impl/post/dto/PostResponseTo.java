package org.example.publisher.impl.post.dto;

import java.math.BigInteger;

public record PostResponseTo(
        BigInteger id,
        BigInteger issueId,
        String content
) {
}
