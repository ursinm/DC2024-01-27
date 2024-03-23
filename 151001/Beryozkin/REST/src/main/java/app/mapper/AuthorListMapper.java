package app.mapper;

import app.dto.AuthorResponseTo;
import app.dto.AuthorRequestTo;
import app.entities.Author;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = AuthorMapper.class)
public interface AuthorListMapper {
    List<Author> toAuthorList(List<AuthorRequestTo> authors);
    List<AuthorResponseTo> toAuthorResponseList(List<Author> authors);
}
