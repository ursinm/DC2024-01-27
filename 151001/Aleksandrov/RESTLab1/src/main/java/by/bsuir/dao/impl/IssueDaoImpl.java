package by.bsuir.dao.impl;

import by.bsuir.dao.IssueDao;
import by.bsuir.entities.Issue;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class IssueDaoImpl implements IssueDao {
    @Override
    public Issue createIssue(String title, String content) {
        return null;
    }

    @Override
    public Issue getIssueByParameters(Set<String> labelNames, Set<Long> labelIds, String editorLogin, String title, String content) {
        return null;
    }

    @Override
    public List<Issue> getIssues() {
        return null;
    }

    @Override
    public Issue getIssueById(Long Id) {
        return null;
    }

    @Override
    public Issue updateIssue(Long id, Issue newIssue) {
        return null;
    }

    @Override
    public void deleteIssue(Long id) {

    }

    @Override
    public Issue getIssueByIssueId(Long issueId) {
        return null;
    }
}
