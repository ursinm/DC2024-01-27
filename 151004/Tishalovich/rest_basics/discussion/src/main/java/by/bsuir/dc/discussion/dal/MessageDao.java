package by.bsuir.dc.discussion.dal;

import by.bsuir.dc.discussion.entity.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageDao extends CrudRepository<Message, Long> {
}
