package com.distributed_computing.jpa.bean.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageResponseTo {
    int id;
    int issueId;
    String content;
}