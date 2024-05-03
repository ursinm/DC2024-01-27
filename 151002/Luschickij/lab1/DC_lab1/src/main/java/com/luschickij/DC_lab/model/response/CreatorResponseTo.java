package com.luschickij.DC_lab.model.response;

public record CreatorResponseTo(
        Long id,
        String login,
        String password,
        String firstname,
        String lastname
) {
}
