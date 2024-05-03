package by.bsuir.repository;

import by.bsuir.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    boolean existsByLogin(String login);

    @Query("SELECT i.user FROM Issue i WHERE i.id = :issueId")
    User findUserByIssueId(@Param("issueId") Long issueId);

    Page<User> findAll(Pageable pageable);
}