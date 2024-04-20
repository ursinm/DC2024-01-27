package by.bsuir.dc.publisher.services.impl;

import by.bsuir.dc.publisher.dal.RedisMessageDao;
import by.bsuir.dc.publisher.entities.dtos.response.MessageResponseTo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class RedisMessageService {

    private final RedisMessageDao dao;

    public void save(MessageResponseTo message) {
        dao.save(message);
    }

    public Optional<MessageResponseTo> findById(Long id) {
        return dao.findById(id);
    }

    public void deleteById(Long id) {
        dao.deleteById(id);
    }

    public void update(MessageResponseTo message) {
        dao.save(message);
    }

    public Iterable<MessageResponseTo> findAll() {
        return StreamSupport.stream(dao.findAll().spliterator(), false)
                .filter(Objects::nonNull)
                .toList();
    }

}
