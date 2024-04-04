package com.distributed_computing.bean.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentRequestTo {
    int id;

    @Min(value = 0)
    int tweetId;

    @Size(min = 2, max = 2048, message = "Incorrect content size")
    String content;
}
