package com.distributed_computing.jpa.repository;

import com.distributed_computing.jpa.bean.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Integer> {
    boolean existsIssueByTitle(String title);
}