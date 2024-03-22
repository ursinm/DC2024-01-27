package com.example.storyteller.dto.responseDto;

import com.example.storyteller.model.Story;
import lombok.Data;

@Data
public class PostResponseTo {

    private Long id;

    private String content;

    private Long storyId;

}
