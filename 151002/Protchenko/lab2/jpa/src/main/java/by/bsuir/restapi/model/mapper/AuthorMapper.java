package by.bsuir.restapi.model.mapper;

import by.bsuir.restapi.model.dto.request.AuthorRequestDto;
import by.bsuir.restapi.model.dto.response.AuthorResponseDto;
import by.bsuir.restapi.model.entity.Author;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthorMapper {

    AuthorResponseDto toDto(Author author);

    Author toEntity(AuthorRequestDto authorRequestDto);

    List<AuthorResponseDto> toDto(List<Author> authors);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    Author partialUpdate(AuthorRequestDto creatorRequestDto, @MappingTarget Author author);
}
