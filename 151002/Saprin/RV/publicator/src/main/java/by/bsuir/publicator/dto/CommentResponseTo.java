package by.bsuir.publicator.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
public class CommentResponseTo {
    private BigInteger id;
    private BigInteger issueId;
    private String content;

    public CommentResponseTo(BigInteger id, BigInteger issueId, String content) {
        this.id = id;
        this.issueId = issueId;
        this.content = content;
    }
}
