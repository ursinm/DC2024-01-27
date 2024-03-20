package by.bsuir.RV_Project.dto.responses.converters;

import by.bsuir.RV_Project.domain.Author;
import by.bsuir.RV_Project.dto.responses.AuthorResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = AuthorResponseConverter.class)
public interface CollectionAuthorResponseConverter {
    List<AuthorResponseDto> toListDto(List<Author> authors);
}