package services.tweetservice.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import services.tweetservice.domain.entity.Message;

@Repository
public interface MessageCacheRepository extends CrudRepository<Message, Long> {
}
