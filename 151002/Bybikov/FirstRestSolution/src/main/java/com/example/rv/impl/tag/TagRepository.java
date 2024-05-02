package com.example.rv.impl.tag;

import com.example.rv.api.MemRepository.MemoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagRepository extends MemoryRepository<Tag> {
    @Override
    public Optional<Tag> save(Tag entity) {
        entity.id = ids.incrementAndGet();
        map.put(entity.getId(), entity);
        return Optional.of(entity);
    }

    @Override
    public Optional<Tag> update(Tag tag) {
        Long id = tag.getId();
        Tag memRepTag = map.get(id);

        if (memRepTag != null &&
                tag.getName().length() > 1 &&
                tag.getName().length() < 33
        ) {
            memRepTag = tag;
        } else return Optional.empty();

        return Optional.of(memRepTag);
    }

    @Override
    public boolean delete(Tag entity) {
        return map.remove(entity.getId(), entity);
    }
}
