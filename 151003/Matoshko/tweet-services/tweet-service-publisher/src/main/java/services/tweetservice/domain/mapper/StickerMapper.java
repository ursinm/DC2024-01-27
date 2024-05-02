package services.tweetservice.domain.mapper;

import org.mapstruct.Mapper;
import services.tweetservice.domain.entity.Sticker;
import services.tweetservice.domain.request.StickerRequestTo;
import services.tweetservice.domain.response.StickerResponseTo;

@Mapper(componentModel = "spring", uses = TweetListMapper.class)
public interface StickerMapper {
    Sticker toSticker(StickerRequestTo stickerRequestTo);

    StickerResponseTo toStickerResponseTo(Sticker sticker);
}
