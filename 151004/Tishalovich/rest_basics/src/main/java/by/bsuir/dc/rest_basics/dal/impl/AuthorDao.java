package by.bsuir.dc.rest_basics.dal.impl;

import by.bsuir.dc.rest_basics.dal.common.MemoryRepository;
import by.bsuir.dc.rest_basics.entities.Author;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class AuthorDao extends MemoryRepository<Author> {

    @Override
    public Optional<Author> update(Author author) {
        Long id = author.getId();
        Author memoryAuthor = map.get(id);
        if (author.getLogin() != null) {
            memoryAuthor.setLogin(author.getLogin());
        }
        if (author.getFirstName() != null) {
            memoryAuthor.setFirstName(author.getFirstName());
        }
        if (author.getLastName() != null) {
            memoryAuthor.setLastName(author.getLastName());
        }
        if (author.getPassword() != null) {
            memoryAuthor.setPassword(author.getPassword());
        }

        return Optional.of(author);
    }

}
