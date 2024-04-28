package app.repository;

import app.entities.Message;
import app.entities.MessageKey;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;

public interface MessageRepository extends CassandraRepository<Message, MessageKey> {
    List<Message> findByTweetId(Long tweetId);
    List<Message> findById (Long id);
    void deleteByCountryAndTweetIdAndId (String country, Long tweetId, Long id);
    int countByCountry (String country);
}
