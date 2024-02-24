package com.distributed_computing.rest.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Comment {
    int id;
    int tweetId;
    String content;
}
