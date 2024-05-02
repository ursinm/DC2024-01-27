package by.haritonenko.rest.mapper;

import by.haritonenko.rest.dto.request.CreateAuthorDto;
import by.haritonenko.rest.dto.request.UpdateAuthorDto;
import by.haritonenko.rest.dto.response.AuthorResponseDto;
import by.haritonenko.rest.model.Author;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface AuthorMapper {

    Author toAuthor(CreateAuthorDto authorRequest);

    AuthorResponseDto toAuthorResponse(Author author);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Author toAuthor(UpdateAuthorDto authorRequest, @MappingTarget Author author);
}
