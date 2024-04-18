package by.bsuir.dc.publisher.services.impl;

import by.bsuir.dc.publisher.dal.RedisLabelDao;
import by.bsuir.dc.publisher.entities.dtos.response.LabelResponseTo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class RedisLabelService {

    private final RedisLabelDao dao;

    public void save(LabelResponseTo label) {
        dao.save(label);
    }

    public Optional<LabelResponseTo> findById(Long id) {
        return dao.findById(id);
    }

    public void deleteById(Long id) {
        dao.deleteById(id);
    }

    public void update(LabelResponseTo label) {
        dao.save(label);
    }

    public Iterable<LabelResponseTo> findAll() {
        return StreamSupport.stream(dao.findAll().spliterator(), false)
                .toList();
    }

}
