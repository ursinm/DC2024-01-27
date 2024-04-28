package by.bsuir.dc.publisher.dal;

import by.bsuir.dc.publisher.entities.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorDao extends CrudRepository<Author, Long> {
}
