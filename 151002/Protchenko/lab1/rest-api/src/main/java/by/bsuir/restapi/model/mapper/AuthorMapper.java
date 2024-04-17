package by.bsuir.restapi.model.mapper;

import by.bsuir.restapi.model.dto.request.AuthorRequestDto;
import by.bsuir.restapi.model.dto.response.AuthorResponseDto;
import by.bsuir.restapi.model.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthorMapper {

    AuthorResponseDto toDto(Author author);

    Author toEntity(AuthorRequestDto authorRequestDto);

    List<AuthorResponseDto> toDto(List<Author> authors);
}
