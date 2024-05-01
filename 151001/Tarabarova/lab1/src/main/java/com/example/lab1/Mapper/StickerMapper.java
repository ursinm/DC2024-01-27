package com.example.lab1.Mapper;

import com.example.lab1.DTO.StickerRequestTo;
import com.example.lab1.DTO.StickerResponseTo;
import com.example.lab1.Model.Sticker;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StickerMapper {
    Sticker stickerRequestToSticker(StickerRequestTo stickerRequestTo);

    StickerResponseTo stickerToStickerResponse(Sticker sticker);
}
