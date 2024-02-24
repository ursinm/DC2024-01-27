package com.distributed_computing.rest.bean.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TweetResponseTo {
    int id;
    int creatorId;
    String title;
    String content;
}
