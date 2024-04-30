package by.bsuir.springapi.service.mapper;

import by.bsuir.springapi.model.entity.Sticker;
import by.bsuir.springapi.model.request.StickerRequestTo;
import by.bsuir.springapi.model.response.StickerResponseTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StickerMapper {
    StickerResponseTo getResponseTo(Sticker sticker);

    List<StickerResponseTo> getListResponseTo(Iterable<Sticker> tags);

    Sticker getTag(StickerRequestTo stickerRequestTo);
}
