package by.bsuir.publicator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigInteger;


@EqualsAndHashCode(callSuper = true)
@Data
public class CommentAddResponseTo extends CommentResponseTo{
    String status;

    public CommentAddResponseTo(BigInteger id, BigInteger issueId, String content, String status) {
        super(id, issueId, content);
        this.status = status;
    }
}
