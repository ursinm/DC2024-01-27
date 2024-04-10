package com.distributed_computing.rest.repository;

import com.distributed_computing.rest.bean.Issue;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class IssueRepository {

    Map<Integer, Issue> issues = new HashMap<>();

    public List<Issue> getAll(){
        List<Issue> res = new ArrayList<>();

        for(Map.Entry<Integer, Issue> entry : issues.entrySet()){
            res.add(entry.getValue());
        }

        return res;
    }

    public Optional<Issue> save(Issue issue){
        Issue prevIssue = issues.getOrDefault(issue.getId(), null);
        issues.put(issue.getId(), issue);
        return Optional.ofNullable(prevIssue);
    }


    public Optional<Issue> getById(int id){
        return Optional.ofNullable(issues.getOrDefault(id, null));
    }

    public Issue delete(int id){
        return issues.remove(id);
    }
}