package org.example.discussion.impl.post.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestTo {
    private BigInteger id;
    private BigInteger issueId;
    @Size(min = 2, max = 2048)
    String content;
}
