package org.example.discussion.impl.post.dto;

import java.math.BigInteger;

public record PostResponseTo(
        BigInteger id,
        BigInteger issueId,
        String content
) {
}
