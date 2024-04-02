package by.bsuir.dc.publisher.dal;

import by.bsuir.dc.publisher.entities.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageDao extends CrudRepository<Message, Long> {
}
