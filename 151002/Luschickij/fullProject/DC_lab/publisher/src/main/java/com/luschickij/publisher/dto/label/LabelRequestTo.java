package com.luschickij.publisher.dto.label;

import com.luschickij.publisher.model.IdentifiedEntity;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LabelRequestTo extends IdentifiedEntity {

    private Long id;
    @Size(min = 2, max = 32)
    private String name;

}
