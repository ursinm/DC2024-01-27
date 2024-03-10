package by.bsuir.dc.repository;

import by.bsuir.dc.entity.Sticker;
import by.bsuir.dc.repository.common.InMemoryCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class StickerRepository extends InMemoryCrudRepository<Sticker> {
}
