package org.example.dc.services;

import org.example.dc.model.Issue;
import org.example.dc.model.IssueDto;

import java.util.List;

public interface IssueService {
    List<IssueDto> getIssues();
    IssueDto getIssueById(int id);
    IssueDto createIssue(IssueDto issueDto);
    boolean delete(int id) throws Exception;
    IssueDto update(IssueDto issueDto);
}
