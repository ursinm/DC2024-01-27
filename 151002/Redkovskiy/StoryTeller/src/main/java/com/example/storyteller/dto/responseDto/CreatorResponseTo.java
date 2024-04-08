package com.example.storyteller.dto.responseDto;

import lombok.Data;

@Data
public class CreatorResponseTo {

    private Long id;

    private String login;

    private String password;

    private String firstname;

    private String lastname;
}
