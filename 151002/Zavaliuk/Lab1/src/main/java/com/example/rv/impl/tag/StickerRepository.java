package com.example.rv.impl.tag;

import com.example.rv.api.MemRepository.MemoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StickerRepository extends MemoryRepository<Sticker> {
    @Override
    public Optional<Sticker> save(Sticker entity) {
        entity.id = ids.incrementAndGet();
        map.put(entity.getId(), entity);
        return Optional.of(entity);
    }

    @Override
    public Optional<Sticker> update(Sticker sticker) {
        Long id = sticker.getId();
        Sticker memRepSticker = map.get(id);

        if (memRepSticker != null &&
                sticker.getName().length() > 1 &&
                sticker.getName().length() < 33
        ) {
            memRepSticker = sticker;
        } else return Optional.empty();

        return Optional.of(memRepSticker);
    }

    @Override
    public boolean delete(Sticker entity) {
        return map.remove(entity.getId(), entity);
    }
}
