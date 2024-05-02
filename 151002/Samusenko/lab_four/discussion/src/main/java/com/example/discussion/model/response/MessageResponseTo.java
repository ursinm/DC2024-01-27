package com.example.discussion.model.response;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
public class MessageResponseTo{
    private Long id;
    private Long issueId;
    private String content;
    public MessageResponseTo(BigInteger id, BigInteger issueId, String content) {
        this.id = id.longValue();
        this.issueId = issueId.longValue();
        this.content = content;
    }
}
