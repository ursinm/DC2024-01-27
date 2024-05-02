package org.example.publisher.impl.sticker.mapper;
import org.example.publisher.impl.sticker.Sticker;
import org.example.publisher.impl.sticker.dto.StickerRequestTo;
import org.example.publisher.impl.sticker.dto.StickerResponseTo;
import org.example.publisher.impl.story.Story;

import java.util.List;

public interface StickerMapper {
    StickerRequestTo stickerToRequestTo(Sticker sticker);

    List<StickerRequestTo> stickerToRequestTo(Iterable<Sticker> stickers);

    Sticker dtoToEntity(StickerRequestTo stickerRequestTo, List<Story> storys);

    StickerResponseTo stickerToResponseTo(Sticker sticker);

    List<StickerResponseTo> stickerToResponseTo(Iterable<Sticker> stickers);
}
