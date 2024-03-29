package by.bsuir.taskrest.repository;

import by.bsuir.taskrest.entity.Message;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface MessageRepository extends ListCrudRepository<Message, Long> {
    Optional<Message> findByStoryId(Long storyId);
}
