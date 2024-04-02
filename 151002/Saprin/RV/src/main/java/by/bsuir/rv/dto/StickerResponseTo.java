package by.bsuir.rv.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Data
@NoArgsConstructor
public class StickerResponseTo {
    private BigInteger id;
    private String name;
    private List<BigInteger> issueIds;

    public StickerResponseTo(BigInteger id, String name, List<BigInteger> issueIds) {
        this.id = id;
        this.name = name;
        this.issueIds = issueIds;
    }
}
