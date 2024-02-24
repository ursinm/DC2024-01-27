package com.distributed_computing.rest.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Tweet {
    int id;
    int creatorId;
    String title;
    String content;
    LocalDateTime created;
    LocalDateTime modified;
}
