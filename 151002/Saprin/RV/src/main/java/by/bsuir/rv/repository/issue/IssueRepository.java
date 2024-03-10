package by.bsuir.rv.repository.issue;

import by.bsuir.rv.bean.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface IssueRepository extends JpaRepository<Issue, BigInteger> {
}
