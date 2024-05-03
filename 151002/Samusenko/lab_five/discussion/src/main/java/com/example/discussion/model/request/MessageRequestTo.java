package com.example.discussion.model.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequestTo{
        Long id;
        Long issueId;
        @Size(min = 2, max = 2048)
        String content;
}
