package com.example.storyteller.dto.responseDto;

import lombok.Data;

@Data
public class StoryResponseTo {

    private Long id;

    private String title;

    private String content;

    private String created;

    private String modified;

    private Long creatorId;
}
