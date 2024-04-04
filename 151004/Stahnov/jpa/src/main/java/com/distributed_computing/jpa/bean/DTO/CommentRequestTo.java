package com.distributed_computing.jpa.bean.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentRequestTo {
    int id;

    @NotNull
    @Min(value = 0)
    Integer tweetId;

    @NotNull
    @Size(min = 2, max = 2048, message = "Incorrect content size")
    String content;
}
