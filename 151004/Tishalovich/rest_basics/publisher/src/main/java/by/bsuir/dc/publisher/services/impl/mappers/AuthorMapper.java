package by.bsuir.dc.publisher.services.impl.mappers;

import by.bsuir.dc.publisher.entities.dtos.request.AuthorRequestTo;
import by.bsuir.dc.publisher.entities.dtos.response.AuthorResponseTo;
import by.bsuir.dc.publisher.entities.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AuthorMapper
        extends EntityMapper<AuthorRequestTo, AuthorResponseTo, Author> {

    @Mapping(source = "firstname", target = "firstName")
    @Mapping(source = "lastname", target = "lastName")
    Author requestToModel(AuthorRequestTo authorRequestTo);

    @Mapping(source = "firstName", target = "firstname")
    @Mapping(source = "lastName", target = "lastname")
    AuthorResponseTo modelToResponse(Author author);

}
