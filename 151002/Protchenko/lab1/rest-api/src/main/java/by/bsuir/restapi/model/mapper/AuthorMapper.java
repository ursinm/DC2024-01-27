package by.bsuir.restapi.model.mapper;

import by.bsuir.restapi.model.dto.request.AuthorRequestTo;
import by.bsuir.restapi.model.dto.response.AuthorResponseTo;
import by.bsuir.restapi.model.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthorMapper {

    AuthorResponseTo toDto(Author author);

    Author toEntity(AuthorRequestTo authorRequestTo);
}
