package com.example.publisher.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Locale;
import com.example.publisher.model.entity.MessageState;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageInTopicTo {
    private Long id;
    private Long issueId;
    private Locale country;
    private String content;
    private MessageState state;
}
