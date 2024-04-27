package com.example.lab1.mapper;

import com.example.lab1.dto.IssueRequestTo;
import com.example.lab1.dto.IssueResponseTo;
import com.example.lab1.model.Issue;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = IssueMapper.class)
public interface IssueListMapper {
    List<Issue> toIssueList(List<IssueRequestTo> list);

    List<IssueResponseTo> toIssueResponseList(List<Issue> list);
}
