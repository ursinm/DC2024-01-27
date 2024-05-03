package com.example.publisher.service.mapper;

import com.example.publisher.model.entity.Issue;
import com.example.publisher.model.request.IssueRequestTo;
import com.example.publisher.model.response.IssueResponseTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IssueMapper {
    IssueResponseTo getResponse(Issue issue);
    List<IssueResponseTo> getListResponse(Iterable<Issue> issues);
    Issue getIssue(IssueRequestTo issueRequestTo);
}