package by.bsuir.rv.dto;

import by.bsuir.rv.bean.IdentifiedBean;
import by.bsuir.rv.bean.Issue;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CommentRequestTo extends IdentifiedBean {
    private BigInteger issueId;
    private String content;

    public CommentRequestTo(BigInteger id, BigInteger issueId, String content) {
        super(id);
        this.issueId = issueId;
        this.content = content;
    }
}
