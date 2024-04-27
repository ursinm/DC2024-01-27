package com.example.discussion.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageRequestTo {
    private Long id;
    private Long storyId;
    @NotBlank
    @Size(min = 2, max = 32)
    private String content;
    private String method;
    private String country;
}
