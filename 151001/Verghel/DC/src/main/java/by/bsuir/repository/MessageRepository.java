package by.bsuir.repository;

import by.bsuir.entities.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long>, JpaSpecificationExecutor<Message> {
    List<Message> findMessagesByIssue_Id(Long issueId);
    Page<Message> findAll(Pageable pageable);
}
