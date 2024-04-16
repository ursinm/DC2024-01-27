package by.bsuir.restapi.repository;

import by.bsuir.restapi.model.entity.Author;
import by.bsuir.restapi.repository.base.InMemoryRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorRepository extends InMemoryRepository<Author> {
}
