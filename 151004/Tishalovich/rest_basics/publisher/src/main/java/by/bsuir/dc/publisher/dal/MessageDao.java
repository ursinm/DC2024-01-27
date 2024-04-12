package by.bsuir.dc.publisher.dal;

import by.bsuir.dc.publisher.entities.Message;

import java.util.Optional;

public interface MessageDao {

    Message save(Message message);

    Optional<Message> findById(Long id);

    Iterable<Message> findAll();

    boolean existsById(Long id);

    void deleteById(Long id);

    Message update(Message message);

}
