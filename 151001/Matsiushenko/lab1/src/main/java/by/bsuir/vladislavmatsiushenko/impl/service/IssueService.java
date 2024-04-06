package by.bsuir.vladislavmatsiushenko.impl.service;

import by.bsuir.vladislavmatsiushenko.api.Service;
import by.bsuir.vladislavmatsiushenko.impl.bean.Issue;
import by.bsuir.vladislavmatsiushenko.api.IssueMapper;
import by.bsuir.vladislavmatsiushenko.impl.dto.IssueRequestTo;
import by.bsuir.vladislavmatsiushenko.impl.dto.IssueResponseTo;
import by.bsuir.vladislavmatsiushenko.impl.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class IssueService implements Service<IssueResponseTo, IssueRequestTo> {
    @Autowired
    private IssueRepository issueRepository;

    public IssueService() {}

    public List<IssueResponseTo> getAll() {
        List<Issue> issueList = issueRepository.getAll();
        List<IssueResponseTo> resultList = new ArrayList<>();
        for (int i = 0; i < issueList.size(); i++) {
            resultList.add(IssueMapper.INSTANCE.IssueToIssueResponseTo(issueList.get(i)));
        }

        return resultList;
    }

    public IssueResponseTo update(IssueRequestTo updatingIssue) {
        Issue issue = IssueMapper.INSTANCE.IssueRequestToToIssue(updatingIssue);
        if (validateIssue(issue)) {
            boolean result = issueRepository.update(issue);
            IssueResponseTo responseTo = result ? IssueMapper.INSTANCE.IssueToIssueResponseTo(issue) : null;

            return responseTo;
        }

        return new IssueResponseTo();
    }

    public IssueResponseTo get(long id) {
        return IssueMapper.INSTANCE.IssueToIssueResponseTo(issueRepository.get(id));
    }

    public IssueResponseTo delete(long id) {
        return IssueMapper.INSTANCE.IssueToIssueResponseTo(issueRepository.delete(id));
    }

    public IssueResponseTo add(IssueRequestTo issueRequestTo) {
        Issue issue = IssueMapper.INSTANCE.IssueRequestToToIssue(issueRequestTo);

        return IssueMapper.INSTANCE.IssueToIssueResponseTo(issueRepository.insert(issue));
    }

    private boolean validateIssue(Issue issue) {
        String title = issue.getTitle();
        String content = issue.getContent();

        return (content.length() >= 4 && content.length() <= 2048) && (title.length() >= 2 && title.length() <= 64);
    }
}
