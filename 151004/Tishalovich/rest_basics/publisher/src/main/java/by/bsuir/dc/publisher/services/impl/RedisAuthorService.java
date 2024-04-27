package by.bsuir.dc.publisher.services.impl;

import by.bsuir.dc.publisher.dal.RedisAuthorDao;
import by.bsuir.dc.publisher.entities.dtos.response.AuthorResponseTo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class RedisAuthorService {

    private final RedisAuthorDao dao;

    public void save(AuthorResponseTo author) {
        dao.save(author);
    }

    public Optional<AuthorResponseTo> findById(Long id) {
        return dao.findById(id);
    }

    public void deleteById(Long id) {
        dao.deleteById(id);
    }

    public void update(AuthorResponseTo author) {
        dao.save(author);
    }

    public Iterable<AuthorResponseTo> findAll() {
        return StreamSupport.stream(dao.findAll().spliterator(), false)
                .filter(Objects::nonNull)
                .toList();
    }

}
