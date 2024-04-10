package com.distributed_computing.rest.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Issue {
    int id;
    int creatorId;
    String title;
    String content;
    LocalDateTime created;
    LocalDateTime modified;
}
