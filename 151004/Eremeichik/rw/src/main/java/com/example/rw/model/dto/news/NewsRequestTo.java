package com.example.rw.model.dto.news;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsRequestTo {
    @NotBlank
    @Size(min = 2, max = 64)
    private String title;
    @NotBlank
    @Size(min = 4, max = 2048)
    private String content;
    private Long userId;
    private Long id;
}
