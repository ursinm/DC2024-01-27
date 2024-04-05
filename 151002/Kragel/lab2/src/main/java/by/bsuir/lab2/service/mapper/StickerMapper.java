package by.bsuir.lab2.service.mapper;

import by.bsuir.lab2.dto.request.StickerRequestDto;
import by.bsuir.lab2.dto.response.StickerResponseDto;
import by.bsuir.lab2.entity.Sticker;
import org.mapstruct.*;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface StickerMapper {

    StickerResponseDto toDto(Sticker entity);

    Sticker toEntity(StickerRequestDto stickerRequestDto);

    List<StickerResponseDto> toDto(Collection<Sticker> entities);

    List<Sticker> toEntity(Collection<StickerRequestDto> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    Sticker partialUpdate(StickerRequestDto stickerRequestDto, @MappingTarget Sticker sticker);
}
