package com.distributed_computing.rest.service;

import com.distributed_computing.rest.repository.IssueRepository;
import com.distributed_computing.rest.bean.Issue;
import com.distributed_computing.rest.repository.CreatorRepository;
import com.distributed_computing.rest.bean.Creator;
import com.distributed_computing.rest.exception.NoSuchIssue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IssueService {

    private static int ind = 0;

    @Autowired
    IssueRepository issueRepository;

    public Issue create(Issue issue){
        issue.setId(ind);
        ind++;

        issue.setCreated(LocalDateTime.now());
        issue.setModified(LocalDateTime.now());
        issueRepository.save(issue);

        return issue;
    }

    public List<Issue> getAll(){
        List<Issue> response = new ArrayList<>();

        return issueRepository.getAll();
    }

    public Optional<Issue> getById(int id){
        return issueRepository.getById(id);
    }

    public Issue update(Issue isuue){
        if(issueRepository.getById(isuue.getId()).isEmpty()) throw new NoSuchIssue("There is no such issue with this id");
        Issue prevIssue = issueRepository.getById(isuue.getId()).get();
        isuue.setCreated(prevIssue.getCreated());
        isuue.setModified(LocalDateTime.now());
        issueRepository.save(isuue);
        return isuue;
    }

    public void delete(int id){
        if(issueRepository.delete(id) == null) throw new NoSuchIssue("There is no such issue with this id");
    }
}