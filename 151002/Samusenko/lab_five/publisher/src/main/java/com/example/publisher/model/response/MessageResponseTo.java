package com.example.publisher.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponseTo{
    public BigInteger id;
    public BigInteger issueId;
    public String content;
}
