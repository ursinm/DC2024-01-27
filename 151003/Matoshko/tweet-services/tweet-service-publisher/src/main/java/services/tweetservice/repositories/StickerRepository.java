package services.tweetservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import services.tweetservice.domain.entity.Sticker;

public interface StickerRepository extends JpaRepository<Sticker, Long> {
}
