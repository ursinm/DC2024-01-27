package by.bsuir.dc.rest_basics.author;

import by.bsuir.dc.rest_basics.entities.Author;
import by.bsuir.dc.rest_basics.entities.dtos.request.AuthorRequestTo;
import by.bsuir.dc.rest_basics.entities.dtos.response.AuthorResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TestAuthorMapper {

    TestAuthorMapper INSTANCE = Mappers.getMapper( TestAuthorMapper.class );

    AuthorRequestTo modelToRequest(Author author);

    AuthorResponseTo requestToResponse(AuthorRequestTo authorRequestTo);
}
