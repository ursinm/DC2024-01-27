package com.distributed_computing.bean.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentResponseTo {
    int id;
    int tweetId;
    String content;
}
