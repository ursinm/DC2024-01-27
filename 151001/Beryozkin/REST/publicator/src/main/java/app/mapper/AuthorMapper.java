package app.mapper;

import app.entities.Author;
import app.dto.AuthorRequestTo;
import app.dto.AuthorResponseTo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author authorRequestToAuthor(AuthorRequestTo authorRequestTo);

    AuthorResponseTo authorToAuthorResponse(Author author);
}

