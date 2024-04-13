package com.example.lab2.mapper;

import com.example.lab2.dto.IssueRequestTo;
import com.example.lab2.dto.IssueResponseTo;
import com.example.lab2.model.Issue;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = IssueMapper.class)
public interface IssueListMapper {
    List<Issue> toIssueList(List<IssueRequestTo> list);

    List<IssueResponseTo> toIssueResponseList(List<Issue> list);
}
