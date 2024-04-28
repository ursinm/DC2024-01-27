package org.education.service;

import org.education.bean.Issue;
import org.education.exception.AlreadyExists;
import org.education.exception.IncorrectValuesException;
import org.education.exception.NoSuchIssue;
import org.education.repository.CreatorRepository;
import org.education.repository.IssueRepository;
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

    public boolean existsWithId(int id){
        return issueRepository.existsById(id);
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