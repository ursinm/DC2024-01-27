package by.bsuir.vladislavmatsiushenko.impl.repository;

import by.bsuir.vladislavmatsiushenko.api.InMemoryRepository;
import by.bsuir.vladislavmatsiushenko.impl.bean.Issue;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class IssueRepository implements InMemoryRepository<Issue> {


    private final Map<Long, Issue> IssueMemory = new HashMap<>();

    @Override
    public Issue get(long id) {
        Issue issue = IssueMemory.get(id);
        if (issue != null) {
            issue.setId(id);
        }

        return issue;
    }

    @Override
    public List<Issue> getAll() {
        List<Issue> issueList = new ArrayList<>();
        for (Long key : IssueMemory.keySet()) {
            Issue issue = IssueMemory.get(key);
            issue.setId(key);
            issueList.add(issue);
        }

        return issueList;
    }

    @Override
    public Issue delete(long id) {
        return IssueMemory.remove(id);
    }

    @Override
    public Issue insert(Issue insertObject) {
        IssueMemory.put(insertObject.getId(), insertObject);

        return insertObject;
    }

    @Override
    public boolean update(Issue updatingValue) {
        return IssueMemory.replace(updatingValue.getId(), IssueMemory.get(updatingValue.getId()), updatingValue);
    }

}
