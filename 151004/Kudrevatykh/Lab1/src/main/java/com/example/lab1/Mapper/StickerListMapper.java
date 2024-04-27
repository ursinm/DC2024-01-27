package com.example.lab1.Mapper;

import com.example.lab1.DTO.StickerRequestTo;
import com.example.lab1.DTO.StickerResponseTo;
import com.example.lab1.Model.Sticker;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = StickerMapper.class)
public interface StickerListMapper {
    List<Sticker> toStickerList(List<StickerRequestTo> list);

    List<StickerResponseTo> toStickerResponseList(List<Sticker> list);
}
