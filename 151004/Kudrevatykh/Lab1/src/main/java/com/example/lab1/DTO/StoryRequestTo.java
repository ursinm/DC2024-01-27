package com.example.lab1.DTO;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StoryRequestTo {
    int id;
    int userId;
    @Size(min = 2, max = 64)
    String title;
    @Size(min = 4, max = 2048)
    String content;
  //  Timestamp created;
    //Timestamp modified;
}
