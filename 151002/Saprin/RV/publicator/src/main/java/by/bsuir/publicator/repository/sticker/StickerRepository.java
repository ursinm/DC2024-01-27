package by.bsuir.publicator.repository.sticker;

import by.bsuir.publicator.bean.Sticker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface StickerRepository extends JpaRepository<Sticker, BigInteger> {
}
