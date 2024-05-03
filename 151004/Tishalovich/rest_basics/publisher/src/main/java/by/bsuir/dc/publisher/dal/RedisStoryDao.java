package by.bsuir.dc.publisher.dal;

import by.bsuir.dc.publisher.entities.dtos.response.StoryResponseTo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisStoryDao extends CrudRepository<StoryResponseTo, Long> {
}
