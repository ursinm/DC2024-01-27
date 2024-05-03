package com.example.publisher.model.response;

import java.math.BigInteger;

public record MessageResponseTo(
        BigInteger id,
        BigInteger issueId,
        String content
) {
}
