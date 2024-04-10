package by.bsuir.dc.rest_basics.dal;

import by.bsuir.dc.rest_basics.entities.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageDao extends CrudRepository<Message, Long> {
}
