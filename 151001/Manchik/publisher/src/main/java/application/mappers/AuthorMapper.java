package application.mappers;

import application.dto.AuthorRequestTo;
import application.dto.AuthorResponseTo;
import application.entites.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author toAuthor(AuthorRequestTo author);
    AuthorResponseTo toAuthorResponse(Author author);
}
