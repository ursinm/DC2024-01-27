package com.distributed_computing.rest.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Message {
    int id;
    int issueId;
    String content;
}
