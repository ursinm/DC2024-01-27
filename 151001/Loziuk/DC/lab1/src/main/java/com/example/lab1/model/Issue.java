package com.example.lab1.model;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Issue {
    int id;
    int creatorId;
    @Size(min = 2, max = 64)
    String title;
    @Size(min = 4, max = 2048)
    String content;
    //Timestamp created;
    //Timestamp modified;

}
