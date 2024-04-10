package by.bsuir.publicator.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private BigInteger com_id;

    private String com_country;

    private BigInteger com_issueId;

    private String com_content;
}
