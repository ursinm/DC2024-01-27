package com.poluectov.rvlab1.dto.marker;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.poluectov.rvlab1.model.IdentifiedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@JsonRootName("marker")
public class MarkerResponseTo extends IdentifiedEntity {

    private String name;

}
