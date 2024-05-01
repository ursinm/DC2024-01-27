package app.mapper;

import app.dto.AuthorRequestTo;
import app.dto.AuthorResponseTo;
import app.entities.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author authorRequestToAuthor(AuthorRequestTo authorRequestTo);

    AuthorResponseTo authorToAuthorResponse(Author author);
}

