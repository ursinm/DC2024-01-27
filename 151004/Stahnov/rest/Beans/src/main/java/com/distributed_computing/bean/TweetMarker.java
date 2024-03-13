package com.distributed_computing.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TweetMarker {
    int id;
    int tweetId;
    int markerId;
}
