package by.bsuir.publicator.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestTo implements Serializable {
    private BigInteger id;
    private BigInteger issueId;

    @Size(min = 2, max = 2048)
    private String content;

}
