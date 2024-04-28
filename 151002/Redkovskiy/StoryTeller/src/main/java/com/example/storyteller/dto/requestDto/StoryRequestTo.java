package com.example.storyteller.dto.requestDto;

import com.example.storyteller.model.Creator;
import com.example.storyteller.model.Post;
import com.example.storyteller.model.StoryMarker;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class StoryRequestTo {

    private Long id;

    @NotNull
    @Size(min = 2, max = 64)
    private String title;

    @Size(min = 4, max = 2048)
    private String content;

    @NotNull
    private Long creatorId;

}
