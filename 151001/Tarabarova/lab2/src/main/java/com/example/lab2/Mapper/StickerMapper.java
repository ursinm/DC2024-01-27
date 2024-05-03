package com.example.lab2.Mapper;

import com.example.lab2.DTO.StickerRequestTo;
import com.example.lab2.DTO.StickerResponseTo;
import com.example.lab2.Model.Sticker;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StickerMapper {
    Sticker stickerRequestToSticker(StickerRequestTo stickerRequestTo);

    StickerResponseTo stickerToStickerResponse(Sticker sticker);
}
