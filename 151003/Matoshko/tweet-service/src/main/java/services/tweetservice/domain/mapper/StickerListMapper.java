package services.tweetservice.domain.mapper;

import org.mapstruct.Mapper;
import services.tweetservice.domain.entity.Sticker;
import services.tweetservice.domain.request.StickerRequestTo;
import services.tweetservice.domain.response.StickerResponseTo;

import java.util.List;

@Mapper(componentModel = "spring", uses = StickerMapper.class)
public interface StickerListMapper {
    List<Sticker> toStickerList(List<StickerRequestTo> stickerRequestToList);

    List<StickerResponseTo> toStickerResponseToList(List<Sticker> stickerList);
}
