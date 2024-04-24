package com.example.discussion.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageKey implements Serializable {
    private String country;
    private Long story_id;
    private Long id;
}
