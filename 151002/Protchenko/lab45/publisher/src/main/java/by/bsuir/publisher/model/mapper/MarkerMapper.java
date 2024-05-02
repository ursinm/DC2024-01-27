package by.bsuir.publisher.model.mapper;

import by.bsuir.publisher.model.dto.request.MarkerRequestDto;
import by.bsuir.publisher.model.dto.response.MarkerResponseDto;
import by.bsuir.publisher.model.entity.Marker;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MarkerMapper {

    MarkerResponseDto toDto(Marker Marker);

    Marker toEntity(MarkerRequestDto MarkerRequestDto);

    List<MarkerResponseDto> toDto(List<Marker> Markers);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    Marker partialUpdate(MarkerRequestDto stickerRequestDto, @MappingTarget Marker sticker);
}
