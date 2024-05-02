package com.example.lab2.Repository;

import com.example.lab2.Model.Sticker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StickerRepository extends JpaRepository<Sticker,Integer> {
    Page<Sticker> findAll(Pageable pageable);
}
