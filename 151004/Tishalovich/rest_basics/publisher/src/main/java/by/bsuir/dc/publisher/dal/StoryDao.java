package by.bsuir.dc.publisher.dal;

import by.bsuir.dc.publisher.entities.Story;
import org.springframework.data.repository.CrudRepository;

public interface StoryDao extends CrudRepository<Story, Long> {
}
