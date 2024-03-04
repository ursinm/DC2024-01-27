package by.bsuir.RV_Project.dto.responses.converters;

import by.bsuir.RV_Project.domain.Author;
import by.bsuir.RV_Project.dto.responses.AuthorResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorResponseConverter {
    AuthorResponseDto toDto(Author author);
}
