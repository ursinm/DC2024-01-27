package com.distributed_computing.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tweet {
    int id;
    int creatorId;
    String title;
    String content;
    LocalDateTime created;
    LocalDateTime modified;
}
