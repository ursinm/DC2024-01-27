package by.bsuir.dc.service.mapper;

import by.bsuir.dc.dto.request.CreatorRequestDto;
import by.bsuir.dc.dto.response.CreatorResponseDto;
import by.bsuir.dc.entity.Creator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CreatorMapper {
    @Mapping(target = "firstname", source = "firstName")
    @Mapping(target = "lastname", source = "lastName")
    CreatorResponseDto toDto(Creator entity);

    @Mapping(target = "firstName", source = "firstname")
    @Mapping(target = "lastName", source = "lastname")
    Creator toEntity(CreatorRequestDto creatorRequestDto);

    List<CreatorResponseDto> toDto(Iterable<Creator> entities);

    List<Creator> toEntity(Iterable<CreatorRequestDto> entities);
}
