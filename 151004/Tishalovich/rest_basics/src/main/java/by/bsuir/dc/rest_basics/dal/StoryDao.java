package by.bsuir.dc.rest_basics.dal;

import by.bsuir.dc.rest_basics.entities.Story;
import org.springframework.data.repository.CrudRepository;

public interface StoryDao extends CrudRepository<Story, Long> {
}
