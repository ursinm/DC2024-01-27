package com.poluectov.rvlab1.dto.message;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.poluectov.rvlab1.dto.issue.IssueResponseTo;
import com.poluectov.rvlab1.model.IdentifiedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@JsonRootName("message")
public class MessageResponseTo extends IdentifiedEntity {

    private IssueResponseTo issue;
    private String content;
}
