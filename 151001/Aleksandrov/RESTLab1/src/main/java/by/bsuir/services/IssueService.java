package by.bsuir.services;

import by.bsuir.dao.IssueDao;
import by.bsuir.dto.IssueRequestTo;
import by.bsuir.dto.IssueResponseTo;
import by.bsuir.entities.Issue;
import by.bsuir.mapper.IssueListMapper;
import by.bsuir.mapper.IssueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueService {
    @Autowired
    IssueMapper issueMapper;
    @Autowired
    IssueDao issueDao;
    @Autowired
    IssueListMapper issueListMapper;

    public IssueResponseTo getIssueById(Long id) {
        Optional<Issue> issue = issueDao.findById(id);
        return issue.map(value -> issueMapper.issueToIssueResponse(value)).orElse(null);
    }

    public List<IssueResponseTo> getIssues() {
        return issueListMapper.toIssueResponseList(issueDao.findAll());
    }

    public IssueResponseTo saveIssue(IssueRequestTo issue){
        Issue issueToSave = issueMapper.issueRequestToIssue(issue);
        return issueMapper.issueToIssueResponse(issueDao.save(issueToSave));
    }

    public void deleteIssue(Long id){
        issueDao.delete(id);
    }

    public IssueResponseTo updateIssue(IssueRequestTo issue, Long id){
        Issue issueToUpdate = issueMapper.issueRequestToIssue(issue);
        return issueMapper.issueToIssueResponse(issueDao.update(issueToUpdate, id));
    }
}
