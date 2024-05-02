package by.bsuir.publicator.repository.issue;

import by.bsuir.publicator.bean.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface IssueRepository extends JpaRepository<Issue, BigInteger> {
}
