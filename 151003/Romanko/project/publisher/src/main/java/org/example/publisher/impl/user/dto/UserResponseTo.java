package org.example.publisher.impl.user.dto;

import java.math.BigInteger;

public record UserResponseTo(
        BigInteger id,
        String login,
        String firstname,
        String lastname
){}