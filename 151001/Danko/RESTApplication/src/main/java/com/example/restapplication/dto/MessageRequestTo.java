package com.example.restapplication.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequestTo {
    private Long id;
    private Long storyId;
    @NotBlank
    @Size(min = 2, max = 32)
    private String content;
    private String country;
    private String method;
}
