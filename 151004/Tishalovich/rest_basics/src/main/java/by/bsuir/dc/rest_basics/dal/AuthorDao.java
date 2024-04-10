package by.bsuir.dc.rest_basics.dal;

import by.bsuir.dc.rest_basics.entities.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorDao extends CrudRepository<Author, Long> {
}
