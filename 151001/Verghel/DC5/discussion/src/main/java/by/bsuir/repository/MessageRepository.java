package by.bsuir.repository;

import by.bsuir.entities.Message;
import by.bsuir.entities.MessageKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends CassandraRepository<Message, MessageKey> {
    List<Message> findByIssueId(int issueId);
    List<Message> findById (int id);
    void deleteByCountryAndIssueIdAndId (String country, int issueId, int id);
    int countByCountry (String country);
}
