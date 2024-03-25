package com.example.restapplication.entites;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Story {
    private Long id;
    private String title;
    private String content;
    private Timestamp created;
    private Timestamp modified;
    private Long userId;
    private Long tagId;
}
