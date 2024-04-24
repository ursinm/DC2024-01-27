package com.example.restapplication.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagResponseTo {
    private Long id;
    private String name;
    private Long storyId;
}
