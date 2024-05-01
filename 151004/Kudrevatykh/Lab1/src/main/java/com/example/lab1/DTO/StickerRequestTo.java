package com.example.lab1.DTO;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StickerRequestTo {
    int id;
    @Size(min = 2, max = 32)
    String name;
    int storyId;
}
