package by.bsuir.restapi.repository;

import by.bsuir.restapi.model.entity.Issue;
import by.bsuir.restapi.repository.base.InMemoryRepository;
import org.springframework.stereotype.Repository;

@Repository
public class IssueRepository extends InMemoryRepository<Issue> {
}
