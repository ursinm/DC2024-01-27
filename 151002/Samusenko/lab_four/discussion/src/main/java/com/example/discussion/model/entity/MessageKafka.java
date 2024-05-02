package com.example.discussion.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageKafka {
    private Message message;
    private MessageState state;
}
