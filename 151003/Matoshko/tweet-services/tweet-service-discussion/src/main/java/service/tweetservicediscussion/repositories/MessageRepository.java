package service.tweetservicediscussion.repositories;

import edu.umd.cs.findbugs.annotations.NonNull;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import service.tweetservicediscussion.domain.entity.Message;

import java.util.Optional;

@Repository
public interface MessageRepository extends CassandraRepository<Message, Long> {
    @AllowFiltering
    Optional<Message> findMessageById(Long id);

    @AllowFiltering
    boolean existsById(@NonNull Long id);
}
