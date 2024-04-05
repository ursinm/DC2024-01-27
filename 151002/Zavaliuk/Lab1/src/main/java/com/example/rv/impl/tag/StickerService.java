package com.example.rv.impl.tag;

import com.example.rv.api.repository.CrudRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StickerService {

    public final CrudRepository<Sticker, Long> tagCrudRepository;

    public final StickerMapperImpl tagMapper;
}
