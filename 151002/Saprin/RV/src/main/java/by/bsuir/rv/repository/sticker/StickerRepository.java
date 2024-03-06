package by.bsuir.rv.repository.sticker;

import by.bsuir.rv.bean.Sticker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface StickerRepository extends JpaRepository<Sticker, BigInteger> {
}
