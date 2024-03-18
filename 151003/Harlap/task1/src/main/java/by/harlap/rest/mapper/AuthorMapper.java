package by.harlap.rest.mapper;

import by.harlap.rest.dto.request.CreateAuthorDto;
import by.harlap.rest.dto.request.UpdateAuthorDto;
import by.harlap.rest.dto.response.AuthorResponseDto;
import by.harlap.rest.model.Author;
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
