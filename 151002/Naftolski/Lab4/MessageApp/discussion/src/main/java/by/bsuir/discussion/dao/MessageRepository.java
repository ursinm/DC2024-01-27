package by.bsuir.discussion.dao;

import by.bsuir.discussion.model.entity.Message;
import by.bsuir.discussion.model.entity.MessageKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageRepository extends CassandraRepository<Message, MessageKey> {
    @Query(allowFiltering = true)
    Optional<Message> findByKeyId(Long id);
}
