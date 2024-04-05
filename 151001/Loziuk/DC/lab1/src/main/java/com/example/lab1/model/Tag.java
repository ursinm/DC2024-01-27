package com.example.lab1.model;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class Tag {
    int id;
    @Size(min = 2, max = 32)
    String name;
    int issueId;
}
