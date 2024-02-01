package com.poluectov.rvlab1.dto.issue;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.poluectov.rvlab1.dto.marker.MarkerResponseTo;
import com.poluectov.rvlab1.dto.user.UserResponseTo;
import com.poluectov.rvlab1.model.IdentifiedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@JsonRootName("issue")
public class IssueResponseTo extends IdentifiedEntity {

    private UserResponseTo user;
    private String title;
    private String content;
    private Date created;
    private Date modified;

    private List<MarkerResponseTo> markers;
}
