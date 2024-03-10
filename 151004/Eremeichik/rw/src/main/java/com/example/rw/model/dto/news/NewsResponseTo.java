package com.example.rw.model.dto.news;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsResponseTo {

    private String title;
    private String content;
    private Long id;
    private Long userId;
    private Date creationDate;
    private Date updateDate;
}
