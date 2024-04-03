package by.bsuir.publisher.service.mapper;

import by.bsuir.publisher.dto.request.CreatorRequestDto;
import by.bsuir.publisher.dto.response.CreatorResponseDto;
import by.bsuir.publisher.model.Creator;
import org.mapstruct.*;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CreatorMapper {
    @Mapping(target = "firstname", source = "firstName")
    @Mapping(target = "lastname", source = "lastName")
    CreatorResponseDto toDto(Creator entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "firstName", source = "firstname")
    @Mapping(target = "lastName", source = "lastname")
    Creator toEntity(CreatorRequestDto creatorRequestDto);

    List<CreatorResponseDto> toDto(Collection<Creator> entities);

    List<Creator> toEntity(Collection<CreatorRequestDto> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "firstName", source = "firstname")
    @Mapping(target = "lastName", source = "lastname")
    Creator partialUpdate(CreatorRequestDto creatorRequestDto, @MappingTarget Creator creator);
}
