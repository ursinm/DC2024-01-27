package by.harlap.jpa.mapper;

import by.harlap.jpa.dto.request.CreateAuthorDto;
import by.harlap.jpa.dto.request.UpdateAuthorDto;
import by.harlap.jpa.dto.response.AuthorResponseDto;
import by.harlap.jpa.model.Author;
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
