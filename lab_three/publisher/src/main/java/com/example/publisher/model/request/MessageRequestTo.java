package com.example.publisher.model.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.math.BigInteger;

public record MessageRequestTo(
        BigInteger id,
        BigInteger issueId,
        @NotBlank
        @Length(min = 2, max = 2048)
        String content
) {
}
