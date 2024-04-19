package com.example.lab1.Model;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Post {
    int id;
    int storyId;
    @Size(min = 2, max = 2048)
    String content;
}
