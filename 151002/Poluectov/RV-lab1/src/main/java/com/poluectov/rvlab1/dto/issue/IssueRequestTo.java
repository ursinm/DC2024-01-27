package com.poluectov.rvlab1.dto.issue;

import com.poluectov.rvlab1.model.IdentifiedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class IssueRequestTo extends IdentifiedEntity {

    private BigInteger userId;
    private String title;
    private String content;
    private Date created;
    private Date modified;

    private List<BigInteger> markers;
}
