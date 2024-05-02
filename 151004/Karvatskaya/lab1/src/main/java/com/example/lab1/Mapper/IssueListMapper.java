package com.example.lab1.Mapper;

import com.example.lab1.DTO.IssueRequestTo;
import com.example.lab1.DTO.IssueResponseTo;
import com.example.lab1.Model.Issue;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = IssueMapper.class)
public interface IssueListMapper {
    List<Issue> toIssueList(List<IssueRequestTo> list);

    List<IssueResponseTo> toIssueResponseList(List<Issue> list);
}
