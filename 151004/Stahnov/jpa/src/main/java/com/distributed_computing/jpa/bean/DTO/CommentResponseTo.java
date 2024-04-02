package com.distributed_computing.jpa.bean.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentResponseTo {
    int id;
    int tweetId;
    String content;
}
