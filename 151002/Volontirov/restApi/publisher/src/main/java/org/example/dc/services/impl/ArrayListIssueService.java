package org.example.dc.services.impl;

import org.example.dc.model.IssueDto;
import org.example.dc.services.IssueService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArrayListIssueService implements IssueService {
    private static int id = 1;
    private List<IssueDto> issues = new ArrayList<>();
    @Override
    public List<IssueDto> getIssues() {
        return issues;
    }

    @Override
    public IssueDto getIssueById(int id) {
        return issues.stream()
                .filter(issue -> issue.getId() == id)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public IssueDto createIssue(IssueDto issueDto) {
        issueDto.setId(id++);
        issueDto.setCreated(new Date(System.currentTimeMillis()));
        issueDto.setModified(new Date(System.currentTimeMillis()));
        issues.add(issueDto);
        return issueDto;
    }

    @Override
    public boolean delete(int id) throws Exception {
        IssueDto issue = issues.stream()
                .filter(is -> is.getId() == id)
                .findFirst()
                .orElseThrow(Exception::new);
        issues.remove(issue);
        return true;
    }

    @Override
    public IssueDto update(IssueDto issueDto) {
        IssueDto issue = issues.stream()
                .filter(issue1 -> issue1.getId() == issueDto.getId())
                .findFirst().get();
        issue.setTitle(issueDto.getTitle());
        issue.setModified(new Date(System.currentTimeMillis()));
        issue.setContent(issueDto.getContent());
        issue.setEditorId(issueDto.getEditorId());
        return issue;
    }
}
