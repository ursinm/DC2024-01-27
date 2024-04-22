package by.bsuir.dc.publisher.dal;

import by.bsuir.dc.publisher.entities.dtos.response.MessageResponseTo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisMessageDao extends CrudRepository<MessageResponseTo, Long> {
}
