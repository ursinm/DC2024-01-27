package com.example.rv.impl.tag;
import java.util.List;

public interface StickerMapper {
    StickerRequestTo tagToRequestTo(Sticker sticker);

    List<StickerRequestTo> tagToRequestTo(Iterable<Sticker> tags);

    Sticker dtoToEntity(StickerRequestTo stickerRequestTo);

    List<Sticker> dtoToEntity(Iterable<StickerRequestTo> tagRequestTos);

    StickerResponseTo tagToResponseTo(Sticker sticker);

    List<StickerResponseTo> tagToResponseTo(Iterable<Sticker> tags);
}
