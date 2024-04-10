package com.example.lab2.mapper;

import com.example.lab2.dto.IssueRequestTo;
import com.example.lab2.dto.IssueResponseTo;
import com.example.lab2.model.Issue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IssueMapper {
    Issue issueRequestToIssue(IssueRequestTo issueRequestTo);
    @Mapping(target = "creatorId", source = "issue.creator.id")

    IssueResponseTo issueToIssueResponse(Issue issue);
}
