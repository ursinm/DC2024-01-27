package com.example.restapplication.dto;

import com.example.restapplication.utils.StringParser;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoryRequestTo {
    private Long id;
    @NotBlank
    @Size(min =  2, max = 64)
    private String title;
    @NotBlank
    @Size(min =  4, max = 2048)
    @JsonDeserialize(using = StringParser.class)
    private String content;
    private Long userId;
    private Long tagId;
}
