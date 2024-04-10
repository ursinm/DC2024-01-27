package by.bsuir.publisher.service.mapper;

import by.bsuir.publisher.dto.request.StickerRequestDto;
import by.bsuir.publisher.dto.response.StickerResponseDto;
import by.bsuir.publisher.model.Sticker;
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
