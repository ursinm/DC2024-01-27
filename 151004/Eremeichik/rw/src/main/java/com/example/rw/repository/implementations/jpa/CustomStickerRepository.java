package com.example.rw.repository.implementations.jpa;

import com.example.rw.model.entity.implementations.Sticker;
import com.example.rw.repository.interfaces.StickerRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public interface CustomStickerRepository extends JpaRepository<Sticker, Long>, StickerRepository {
}
