package com.poluectov.rvlab1.dto.message;

import com.poluectov.rvlab1.model.IdentifiedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class MessageRequestTo extends IdentifiedEntity {

    private BigInteger issueId;
    private String content;

}
