package by.bsuir.taskrest.repository.implementations;

import by.bsuir.taskrest.entity.Message;
import by.bsuir.taskrest.repository.MessageRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MessageRepositoryImpl extends InMemoryRepository<Message> implements MessageRepository {
    @Override
    public Optional<Message> findByStoryId(Long storyId) {
        return entities.values()
                .stream()
                .filter(message -> message.getStoryId().equals(storyId))
                .findFirst();
    }
}
