package by.bsuir.dao;

import by.bsuir.entities.Issue;

import java.util.List;
import java.util.Set;

public interface IssueDao {
    Issue createIssue(String title, String content);

    Issue getIssueByParameters(Set<String> labelNames, Set<Long> labelIds, String editorLogin, String title, String content);

    List<Issue> getIssues();

    Issue getIssueById(Long Id);

    Issue updateIssue(Long id, Issue newIssue);

    void deleteIssue(Long id);

    Issue getIssueByIssueId(Long issueId);
}
