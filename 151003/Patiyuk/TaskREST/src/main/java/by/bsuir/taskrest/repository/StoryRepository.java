package by.bsuir.taskrest.repository;

import by.bsuir.taskrest.entity.Story;
import org.springframework.data.repository.ListCrudRepository;

public interface StoryRepository extends ListCrudRepository<Story, Long> {
}
