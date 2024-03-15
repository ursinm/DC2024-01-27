package by.bsuir.dc.service.mapper;

import by.bsuir.dc.dto.request.StickerRequestDto;
import by.bsuir.dc.dto.response.StickerResponseDto;
import by.bsuir.dc.entity.Sticker;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StickerMapper {

    StickerResponseDto toDto(Sticker entity);

    Sticker toEntity(StickerRequestDto stickerRequestDto);

    List<StickerResponseDto> toDto(Iterable<Sticker> entities);

    List<Sticker> toEntity(Iterable<StickerRequestDto> entities);
}
