package by.bsuir.publicator.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StickerRequestTo {
    private BigInteger id;

    @Size(min = 2, max = 32)
    private String name;
    private List<BigInteger> issueIds;
}
