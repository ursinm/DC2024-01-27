package com.example.rw.repository.implementations.in_memory;

import com.example.rw.model.entity.implementations.Sticker;
import com.example.rw.repository.interfaces.StickerRepository;
import org.springframework.stereotype.Repository;

@Repository
public class StickerInMemoryRepository extends InMemoryRepositoryWithLongId<Sticker> implements StickerRepository {
}
