package by.bsuir.repository;

import by.bsuir.entities.Creator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CreatorRepository extends JpaRepository<Creator, Long>, JpaSpecificationExecutor<Creator> {
    boolean existsByLogin (String login);
    @Query("SELECT i.creator FROM Issue i WHERE i.id = :issueId")
    Creator findCreatorByIssueId(@Param("issueId") Long issueId);

    Page<Creator> findAll(Pageable pageable);
}
