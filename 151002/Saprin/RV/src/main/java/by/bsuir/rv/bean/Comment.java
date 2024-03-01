package by.bsuir.rv.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Comment extends IdentifiedBean {
    private BigInteger issueId;
    private String content;

    public Comment(BigInteger id, BigInteger issueId, String content) {
        super(id);
        this.issueId = issueId;
        this.content = content;
    }
}
