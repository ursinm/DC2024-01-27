package com.example.rv.impl.editor;

public record EditorRequestTo(
    Long id,
    String login,
    String password,
    String firstname,
    String lastname
){}
