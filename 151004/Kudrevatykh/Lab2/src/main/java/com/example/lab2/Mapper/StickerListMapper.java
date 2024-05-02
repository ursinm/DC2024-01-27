package com.example.lab2.Mapper;

import com.example.lab2.DTO.StickerRequestTo;
import com.example.lab2.DTO.StickerResponseTo;
import com.example.lab2.Model.Sticker;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = StickerMapper.class)
public interface StickerListMapper {
    List<Sticker> toStickerList(List<StickerRequestTo> list);

    List<StickerResponseTo> toStickerResponseList(List<Sticker> list);
}
