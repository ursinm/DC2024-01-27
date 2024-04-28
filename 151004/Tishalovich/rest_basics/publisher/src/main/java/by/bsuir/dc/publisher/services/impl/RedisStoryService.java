package by.bsuir.dc.publisher.services.impl;

import by.bsuir.dc.publisher.dal.RedisStoryDao;
import by.bsuir.dc.publisher.entities.dtos.response.StoryResponseTo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class RedisStoryService {

    private final RedisStoryDao dao;

    public void save(StoryResponseTo story) {
        dao.save(story);
    }

    public Optional<StoryResponseTo> findById(Long id) {
        return dao.findById(id);
    }

    public void deleteById(Long id) {
        dao.deleteById(id);
    }

    public void update(StoryResponseTo story) {
        dao.save(story);
    }

    public Iterable<StoryResponseTo> findAll() {
        return StreamSupport.stream(dao.findAll().spliterator(), false)
                .filter(Objects::nonNull)
                .toList();
    }

}
