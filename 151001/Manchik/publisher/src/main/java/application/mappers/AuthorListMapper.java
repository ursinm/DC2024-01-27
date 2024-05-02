package application.mappers;

import application.dto.AuthorRequestTo;
import application.dto.AuthorResponseTo;
import application.entites.Author;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = AuthorMapper.class)
public interface AuthorListMapper {
    List<Author> toAuthorList(List<AuthorRequestTo> author);
    List<AuthorResponseTo> toAuthorResponseList(List<Author> author);
}
