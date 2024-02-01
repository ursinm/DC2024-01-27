package com.poluectov.rvlab1.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class Marker extends IdentifiedEntity {

    private String name;

}
