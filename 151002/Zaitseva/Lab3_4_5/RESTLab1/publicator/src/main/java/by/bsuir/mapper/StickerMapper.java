package by.bsuir.mapper;

import by.bsuir.dto.StickerRequestTo;
import by.bsuir.dto.StickerResponseTo;
import by.bsuir.entities.Sticker;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface StickerMapper {
    Sticker stickerRequestToSticker (StickerRequestTo stickerRequestTo);
    StickerResponseTo stickerToStickerResponse(Sticker sticker);
}

