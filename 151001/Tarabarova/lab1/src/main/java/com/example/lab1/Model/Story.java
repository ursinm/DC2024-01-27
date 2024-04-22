package com.example.lab1.Model;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.security.Timestamp;

@Data
public class Story {
    int id;
    int editorId;
    @Size(min = 2, max = 64)
    String title;
    @Size(min = 4, max = 2048)
    String content;
 //   Timestamp created;
  //  Timestamp modified;

}
