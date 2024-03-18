package com.example.restapplication.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagRequestTo {
    private Long id;
    @NotBlank
    @Size(min = 2, max = 32)
    private String name;
    private Long storyId;
}
