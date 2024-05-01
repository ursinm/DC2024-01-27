package com.example.rv.impl.tweet;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Tweet {
    Long id;
    Long editorId;
    String title;
    String content;
    String created;
    String modified;
}
