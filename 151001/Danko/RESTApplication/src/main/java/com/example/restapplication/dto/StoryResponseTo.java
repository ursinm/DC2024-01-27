package com.example.restapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoryResponseTo {
    private Long id;
    private String title;
    private String content;
    private Timestamp created;
    private Timestamp modified;
    private Long userId;
    private Long tagId;
}
