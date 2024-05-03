package com.example.publisher.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigInteger;

@EqualsAndHashCode(callSuper = true)
@Data
public class MessageCreateResponseTo extends MessageResponseTo{
    String status;

    public MessageCreateResponseTo(BigInteger id, BigInteger issueId, String content, String status) {
        super(id, issueId, content);
        this.status = status;
    }
}
