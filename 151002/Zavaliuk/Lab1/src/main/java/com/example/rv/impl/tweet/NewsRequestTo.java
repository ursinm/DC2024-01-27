package com.example.rv.impl.tweet;

public record NewsRequestTo(
        Long id,
        Long editorId,
        String title,
        String content,
        String created,
        String modified
){

}
