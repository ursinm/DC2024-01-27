package com.luschickij.publisher.dto.label;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.luschickij.publisher.model.IdentifiedEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@JsonRootName("label")
@AllArgsConstructor
@NoArgsConstructor
public class LabelResponseTo extends IdentifiedEntity {

    private Long id;
    private String name;

}
