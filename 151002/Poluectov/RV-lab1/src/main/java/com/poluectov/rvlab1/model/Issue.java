package com.poluectov.rvlab1.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class Issue extends IdentifiedEntity {

    private User user;
    private String title;
    private String content;
    private Date created;
    private Date modified;

    private List<Marker> markers;
}
