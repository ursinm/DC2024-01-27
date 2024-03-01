package by.bsuir.rv.util.converter.sticker;

import by.bsuir.rv.bean.Sticker;
import by.bsuir.rv.dto.StickerRequestTo;
import by.bsuir.rv.dto.StickerResponseTo;
import by.bsuir.rv.util.converter.IConverter;
import org.springframework.stereotype.Component;

@Component
public class StickerConverter implements IConverter<Sticker, StickerResponseTo, StickerRequestTo> {
    public StickerResponseTo convertToResponse(Sticker sticker) {
        return new StickerResponseTo(sticker.getId(), sticker.getName(), sticker.getIssueId());
    }

    @Override
    public Sticker convertToEntity(StickerRequestTo stickerRequestTo) {
        return new Sticker(stickerRequestTo.getId(), stickerRequestTo.getName(), stickerRequestTo.getIssueId());
    }
}
