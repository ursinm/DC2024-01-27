package by.bsuir.rv.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Sticker extends IdentifiedBean {
    private String name;
    private BigInteger issueId;

    public Sticker(BigInteger id, String name, BigInteger issueId) {
        super(id);
        this.name = name;
        this.issueId = issueId;
    }
}
