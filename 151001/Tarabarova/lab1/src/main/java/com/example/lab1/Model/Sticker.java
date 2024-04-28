package com.example.lab1.Model;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class Sticker {
    int id;
    @Size(min = 2, max = 32)
    String name;
    int storyId;
}
