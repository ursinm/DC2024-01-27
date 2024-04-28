package by.bsuir.discussion.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestTo {
    private BigInteger id;
    private BigInteger issueId;


    @Size(min = 2, max = 2048)
    private String content;

}
