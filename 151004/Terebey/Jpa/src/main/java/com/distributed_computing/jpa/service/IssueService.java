package com.distributed_computing.jpa.service;

import com.distributed_computing.jpa.bean.Issue;
import com.distributed_computing.jpa.exception.AlreadyExists;
import com.distributed_computing.jpa.exception.IncorrectValuesException;
import com.distributed_computing.jpa.exception.NoSuchIssue;
import com.distributed_computing.jpa.repository.CreatorRepository;
import com.distributed_computing.jpa.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IssueService {

    final IssueRepository issueRepository;
    final CreatorRepository creatorRepository;

    @Autowired
    public IssueService(IssueRepository issueRepository, CreatorRepository creatorRepository) {
        this.issueRepository = issueRepository;
        this.creatorRepository = creatorRepository;
    }

    public Issue create(Issue issue, int ownerId){
        if(issueRepository.existsIssueByTitle(issue.getTitle())) throw new AlreadyExists("Issue with this title already exists");
        if(!creatorRepository.existsById(ownerId)) throw new IncorrectValuesException("There is no creator with this id");
        issue.setCreator(creatorRepository.getReferenceById(ownerId));
        issue.setCreated(LocalDateTime.now());
        issue.setModified(LocalDateTime.now());
        issueRepository.save(issue);

        return issue;
    }

    public List<Issue> getAll(){
        return issueRepository.findAll();
    }

    public Issue getById(int id){

        return issueRepository.getReferenceById(id);
    }

    public Issue update(Issue issue){
        if(!issueRepository.existsById(issue.getId())) throw new NoSuchIssue("There is no such issue with this id");
        Issue prevIssue = issueRepository.getReferenceById(issue.getId());
        issue.setCreated(prevIssue.getCreated());
        issue.setModified(LocalDateTime.now());
        issueRepository.save(issue);
        return issue;
    }

    public void delete(int id){
        if(!issueRepository.existsById(id)) throw new NoSuchIssue("There is no such issue with this id");
        issueRepository.deleteById(id);
    }
}