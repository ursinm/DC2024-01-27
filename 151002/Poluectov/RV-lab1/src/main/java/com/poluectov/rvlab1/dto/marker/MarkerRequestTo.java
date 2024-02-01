package com.poluectov.rvlab1.dto.marker;

import com.poluectov.rvlab1.model.IdentifiedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class MarkerRequestTo extends IdentifiedEntity {

    private String name;

}
