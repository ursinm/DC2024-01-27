package com.example.rv.impl.editor;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Editor {
    Long id;
    String login;
    String password;
    String firstname;
    String lastname;
}
