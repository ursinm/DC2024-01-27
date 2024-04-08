package by.bsuir.publicator.service.sticker;

import by.bsuir.publicator.dto.StickerRequestTo;
import by.bsuir.publicator.dto.StickerResponseTo;
import by.bsuir.publicator.exception.EntityNotFoundException;

import java.math.BigInteger;
import java.util.List;

public interface IStickerService {
    List<StickerResponseTo> getStickers();
    StickerResponseTo getStickerById(BigInteger id) throws EntityNotFoundException;
    StickerResponseTo createSticker(StickerRequestTo sticker);
    StickerResponseTo updateSticker(StickerRequestTo sticker) throws EntityNotFoundException;
    void deleteSticker(BigInteger id) throws EntityNotFoundException;

}
