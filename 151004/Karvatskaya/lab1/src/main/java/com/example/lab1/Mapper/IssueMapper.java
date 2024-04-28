package com.example.lab1.Mapper;

import com.example.lab1.DTO.IssueRequestTo;
import com.example.lab1.DTO.IssueResponseTo;
import com.example.lab1.Model.Issue;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IssueMapper {
    Issue issueReguestToIssue(IssueRequestTo issueRequestTo);

    IssueResponseTo issueToIssueResponse(Issue issue);
}
