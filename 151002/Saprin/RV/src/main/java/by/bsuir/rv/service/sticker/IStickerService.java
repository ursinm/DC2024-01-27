package by.bsuir.rv.service.sticker;

import by.bsuir.rv.dto.StickerRequestTo;
import by.bsuir.rv.dto.StickerResponseTo;
import by.bsuir.rv.exception.EntityNotFoundException;

import java.math.BigInteger;
import java.util.List;

public interface IStickerService {
    List<StickerResponseTo> getStickers();
    StickerResponseTo getStickerById(BigInteger id) throws EntityNotFoundException;
    StickerResponseTo createSticker(StickerRequestTo sticker);
    StickerResponseTo updateSticker(StickerRequestTo sticker) throws EntityNotFoundException;
    void deleteSticker(BigInteger id) throws EntityNotFoundException;

}
