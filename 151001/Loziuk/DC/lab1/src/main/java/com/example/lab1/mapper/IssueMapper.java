package com.example.lab1.mapper;

import com.example.lab1.dto.IssueRequestTo;
import com.example.lab1.dto.IssueResponseTo;
import com.example.lab1.model.Issue;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IssueMapper {
    Issue issueReguestToIssue(IssueRequestTo issueRequestTo);

    IssueResponseTo issueToIssueResponse(Issue issue);
}
