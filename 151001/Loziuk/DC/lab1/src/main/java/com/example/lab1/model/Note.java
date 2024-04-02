package com.example.lab1.model;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Note {
    int id;
    int issueId;
    @Size(min = 2, max = 2048)
    String content;
}
