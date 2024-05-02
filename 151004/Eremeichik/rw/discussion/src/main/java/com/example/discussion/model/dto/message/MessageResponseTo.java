package com.example.discussion.model.dto.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponseTo implements Serializable {

    private String content;
    private Long newsId;
    private Long id;
}
