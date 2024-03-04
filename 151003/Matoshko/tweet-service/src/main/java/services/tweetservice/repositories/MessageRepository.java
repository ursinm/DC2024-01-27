package services.tweetservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import services.tweetservice.domain.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
