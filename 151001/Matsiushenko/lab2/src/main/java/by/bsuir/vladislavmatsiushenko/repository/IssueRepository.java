package by.bsuir.vladislavmatsiushenko.repository;

import by.bsuir.vladislavmatsiushenko.model.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Integer> {
    Page<Issue> findAll(Pageable pageable);

    boolean existsByTitle(String title);

}


