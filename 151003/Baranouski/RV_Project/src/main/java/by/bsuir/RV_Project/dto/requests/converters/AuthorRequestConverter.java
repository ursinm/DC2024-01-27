package by.bsuir.RV_Project.dto.requests.converters;

import by.bsuir.RV_Project.domain.Author;
import by.bsuir.RV_Project.dto.requests.AuthorRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorRequestConverter {
    Author fromDto(AuthorRequestDto author);
}
