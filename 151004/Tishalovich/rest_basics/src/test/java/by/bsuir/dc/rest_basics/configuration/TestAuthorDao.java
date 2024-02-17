package by.bsuir.dc.rest_basics.configuration;

import by.bsuir.dc.rest_basics.dal.impl.AuthorDao;
import by.bsuir.dc.rest_basics.entities.Author;
import lombok.Getter;

import java.util.Optional;

@Getter
public class TestAuthorDao extends AuthorDao {

    private Long nextId = 1L;

    public void clear() {
        map.clear();
    }

    @Override
    public Optional<Author> save(Author author) {
        nextId += 1;
        return super.save(author);
    }

}
