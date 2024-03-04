package by.bsuir.taskrest.repository.implementations;

import by.bsuir.taskrest.entity.Story;
import by.bsuir.taskrest.repository.StoryRepository;
import org.springframework.stereotype.Repository;

@Repository
public class StoryRepositoryImpl extends InMemoryRepository<Story> implements StoryRepository {
}
